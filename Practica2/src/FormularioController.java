import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import java.sql.*;

public class  FormularioController {
    @FXML
    private TextField notaTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField edadTextField;
    @FXML
    private Label mensajeLabel;
    @FXML
    private ComboBox<String> rolesComboBox;
    @FXML
    private Button eliminarButton;
    @FXML
    private TableView<Usuario> tableView;
    @FXML
    private TableColumn<Usuario, String> nombreColumn;
    @FXML
    private TableColumn<Usuario, Integer> edadColumn;
    @FXML
    private TableColumn<Usuario, Integer> notaColumn;

    @FXML
    private ObservableList<Usuario> usuariosList;

    @FXML
    public void initialize() {
        // Configuración inicial de columnas y datos
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        cargarDatos();
        configurarValidaciones();
        configurarComboBox();
        configurarFocusListener();

        // Configuración de Tooltips
        Tooltip nombreTooltip = new Tooltip("Ingrese un nombre válido (solo letras, entre 3 y 25 caracteres).");
        nombreTextField.setTooltip(nombreTooltip);

        Tooltip edadTooltip = new Tooltip("Ingrese un número positivo.");
        edadTextField.setTooltip(edadTooltip);

        Tooltip notaTooltip = new Tooltip ("Introduce una nota");
        notaTextField.setTooltip(notaTooltip);


    }
    public TextField getNotaTextField() {
        return notaTextField;
    }

    public Label getMensajeLabel() {
        return mensajeLabel;
    }

    private void cargarDatos() {
        usuariosList = FXCollections.observableArrayList();
        try {
            Connection conn = ConexionBD.conectar();
            String sql = "SELECT id, nombre, edad, nota FROM usuarios";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usuariosList.add(new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"),rs.getInt("nota")));
            }
            tableView.setItems(usuariosList);
            aplicarFade(tableView); // Añade la animación de fade
            stmt.close();
            ConexionBD.cerrarConexion();
        } catch (SQLException e) {
            mensajeLabel.setText("Error al cargar los datos.");
            e.printStackTrace();
        }
    }

    private void configurarValidaciones() {
        // Validación dinámica para el campo Nombre
        nombreTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !newValue.matches("[a-zA-Z\\s]{3,25}")) {
                aplicarEstilo(nombreTextField, "lightcoral", "Ingrese un nombre válido (3-25 letras).");
            } else {
                aplicarEstilo(nombreTextField, "lightgreen", "");
            }
        });

        // Validación dinámica para el campo Edad
        edadTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (edadTextField.isDisabled()) {
                // Si el campo está deshabilitado, no aplicamos ningún estilo ni validación
                return;
            }

            if (newValue.isEmpty()) {
                aplicarEstilo(edadTextField, "lightcoral", "El campo Edad está vacío.");
            } else {
                try {
                    int edad = Integer.parseInt(newValue);
                    if (edad > 0) {
                        aplicarEstilo(edadTextField, "lightgreen", "");
                    } else {
                        aplicarEstilo(edadTextField, "lightcoral", "La edad debe ser mayor a 0.");
                    }
                } catch (NumberFormatException e) {
                    aplicarEstilo(edadTextField, "lightcoral", "La edad debe ser un número válido.");
                }
            }
        });

        // Validación dinámica para el campo Nota
        notaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                aplicarEstilo(notaTextField, "lightcoral", "El campo Nota está vacío.");
            } else {
                try {
                    double nota = Double.parseDouble(newValue);
                    if (nota >= 0 && nota <= 10) {
                        aplicarEstilo(notaTextField, "lightgreen", "");
                    } else {
                        aplicarEstilo(notaTextField, "lightcoral", "La nota debe estar entre 0 y 10.");
                    }
                } catch (NumberFormatException e) {
                    aplicarEstilo(notaTextField, "lightcoral", "La nota debe ser un número válido.");
                }
            }
        });
    }


    private void configurarComboBox() {
        rolesComboBox.setItems(FXCollections.observableArrayList("Usuario Estándar", "Administrador", "Invitado"));

        // Configuración de Tooltips para cada opción del ComboBox
        rolesComboBox.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        setText(item);
                        Tooltip tooltip = new Tooltip();
                        switch (item) {
                            case "Usuario Estándar":
                                tooltip.setText("Permite editar Nombre y Edad.");
                                break;
                            case "Administrador":
                                tooltip.setText("Acceso completo a todas las funciones.");
                                break;
                            case "Invitado":
                                tooltip.setText("Solo puede editar el Nombre.");
                                break;
                        }
                        setTooltip(tooltip);
                    }
                }
            };
            return cell;
        });

        rolesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Usuario Estándar":
                    habilitarCampos(true, true);
                    eliminarButton.setDisable(false);
                    mensajeLabel.setText("");
                    break;
                case "Invitado":
                    habilitarCampos(true, false);
                    eliminarButton.setDisable(true);
                    mensajeLabel.setText("Modo invitado: solo se puede editar el Nombre.");
                    break;
                case "Administrador":
                    habilitarCampos(true, true);
                    eliminarButton.setDisable(false);
                    mensajeLabel.setText("Acceso a opciones avanzadas.");
                    break;
            }
        });
    }

    private void habilitarCampos(boolean habilitarNombre, boolean habilitarEdad) {
        nombreTextField.setDisable(!habilitarNombre);
        edadTextField.setDisable(!habilitarEdad);
    }



    private void animarCambioColor(TextField campo, String colorInicioHex, String colorFinHex) {
        Color colorInicio = Color.web(colorInicioHex);
        Color colorFin = Color.web(colorFinHex);

        Timeline timeline = new Timeline();
        final int duracionMilisegundos = 500; // Duración de la animación en ms
        final int pasos = 20; // Cantidad de pasos para hacer la transición suave

        for (int i = 0; i <= pasos; i++) {
            double fraccion = i / (double) pasos;
            Color colorInterpolado = colorInicio.interpolate(colorFin, fraccion);

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(i * (duracionMilisegundos / pasos)),
                    e -> campo.setStyle("-fx-background-color: " + convertirColorAHex(colorInterpolado) + ";")
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }

    private String convertirColorAHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    // Modifica aplicarEstilo para usar la nueva animación
    private void aplicarEstilo(TextField campo, String color, String mensaje) {
        String colorActual = extraerColorActual(campo);
        animarCambioColor(campo, colorActual, color);
        mensajeLabel.setText(mensaje);
    }

    // Método para extraer el color actual de un TextField (reutiliza el anterior)
    private String extraerColorActual(TextField campo) {
        String estiloActual = campo.getStyle();
        if (estiloActual.contains("-fx-background-color")) {
            int inicio = estiloActual.indexOf(":") + 1;
            int fin = estiloActual.indexOf(";", inicio);
            // Verificar si fin es válido
            if (fin == -1) {
                fin = estiloActual.length(); // Si no hay ';', tomar hasta el final
            }
            return estiloActual.substring(inicio, fin).trim();
        }
        return "#FFFFFF"; // Valor por defecto si no hay color definido
    }



    private boolean validarEntradas() {
        String nombre = nombreTextField.getText();
        String edadText = edadTextField.getText();
        String nota = notaTextField.getText();

        //Validar nota
        if(nota.isEmpty()){
            aplicarEstilo(notaTextField,"lightcoral", "La nota no puede estar vacía");
            return false;
        }else{
            aplicarEstilo(notaTextField, "lightgreen","");
        }

        // Validar Nombre
        if (nombre.isEmpty()) {
            //mensajeLabel.setText("Por favor, ingrese un nombre válido (solo letras, entre 3 y 25 caracteres).");
            aplicarEstilo(nombreTextField, "lightcoral", "Por favor ingrese un nombre");
            return false;
        }
        else if(!nombre.matches("[a-zA-Z\\s]{3,25}")){
            aplicarEstilo(nombreTextField,"lightcoral", "Ingrese un nombre valido");
            return false;
        }

        // Validar Edad (solo si no está deshabilitada)
        if (!edadTextField.isDisabled()) {
            if (edadText.isEmpty()) {
                //mensajeLabel.setText("Por favor, complete el campo Edad.");
                aplicarEstilo(edadTextField, "lightcoral", "El campo Edad está vacío.");
                return false;
            }

            try {
                int edad = Integer.parseInt(edadText);
                if (edad <= 0) {
                    //mensajeLabel.setText("La edad debe ser un número mayor a 0.");
                    aplicarEstilo(edadTextField, "lightcoral", "La edad debe ser mayor a 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                //mensajeLabel.setText("La edad debe ser un número válido.");
                aplicarEstilo(edadTextField, "lightcoral", "La edad debe ser un número válido.");
                return false;
            }
        }

        // Si todas las validaciones pasan
        mensajeLabel.setText("");
        return true;
    }

    @FXML
    public void onEnviarClick() {
        if (validarEntradas()) {
            guardarUsuario();
            mensajeLabel.setText("Datos enviados correctamente.");
            mostrarAlerta("Confirmación", "Los datos se han enviado correctamente.");
            limpiarCampos();
        }
    }

    private void guardarUsuario() {
        String nombre = nombreTextField.getText();
        int edad;
        int nota = Integer.parseInt((notaTextField.getText()));

        // Determinar la edad a guardar
        if (edadTextField.isDisabled()) {
            // Mantener la edad anterior si está deshabilitado
            Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();
            edad = usuarioSeleccionado != null ? usuarioSeleccionado.getEdad() : 0; // Si no hay selección, usar 0
        } else {
            edad = Integer.parseInt(edadTextField.getText());
        }

        Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();

        try {
            Connection conn = ConexionBD.conectar();
            String sql;

            if (usuarioSeleccionado == null) {
                // Inserción de un nuevo usuario
                sql = "INSERT INTO usuarios (nombre, edad, nota) VALUES (?, ?, ?)";
            } else {
                // Actualización de un usuario existente
                sql = "UPDATE usuarios SET nombre = ?, edad = ?, nota = ? WHERE id = ?";
                ;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, edad);
            stmt.setInt(3,nota);
            if (usuarioSeleccionado != null) stmt.setInt(4, usuarioSeleccionado.getId());

            stmt.executeUpdate();
            cargarDatos(); // Refrescar la tabla
            aplicarFade(tableView); // Añade la animación de fade
            stmt.close();
            ConexionBD.cerrarConexion();
        } catch (SQLException e) {
            mensajeLabel.setText("Error al guardar los datos.");
            e.printStackTrace();
        }
    }

    @FXML
    public void onEliminarClick() {
        Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirmación");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("¿Estás seguro de eliminar este usuario?");
            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    eliminarUsuario(usuarioSeleccionado);
                }
            });
        } else {
            mensajeLabel.setText("Selecciona un usuario para eliminar.");
        }
    }

    private void eliminarUsuario(Usuario usuario) {
        try {
            Connection conn = ConexionBD.conectar();
            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.executeUpdate();
            cargarDatos();
            aplicarFade(tableView); // Añade la animación de fade
            stmt.close();
            ConexionBD.cerrarConexion();
            limpiarCampos();
            mensajeLabel.setText("Usuario eliminado: " + usuario.getNombre());
        } catch (SQLException e) {
            mensajeLabel.setText("Error al eliminar el registro.");
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        nombreTextField.clear();
        edadTextField.clear();
        nombreTextField.setStyle("-fx-background-color: white;");
        edadTextField.setStyle("-fx-background-color: white;");
        mensajeLabel.setText("");
        tableView.getSelectionModel().clearSelection();

        //no limpiar el contenido de notaTextField
        notaTextField.setStyle("-fx-background-color: white;");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        //alert.getDialogPane().getStylesheets().add(getClass().getResource("res/styles.css").toExternalForm());

        alert.showAndWait();
    }

    private void aplicarFade(TableView<Usuario> tabla) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), tabla);
        fade.setFromValue(0.0); // Empieza invisible
        fade.setToValue(1.0);  // Llega a completamente visible
        fade.setCycleCount(1); // Solo una vez
        fade.play();
    }
    private void configurarFocusListener(){
        agregarFocusListener(nombreTextField);
        agregarFocusListener(edadTextField);
    }
    private void agregarFocusListener(TextField campo) {
        campo.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Si el campo tiene foco, agrega un borde azul
                campo.setStyle("-fx-border-color: blue; -fx-background-color: white");
            } else {
                // Si el campo pierde el foco, vuelve a su estilo o validación por defecto
                if (campo.getStyle().contains("light")) {
                    // Mantenemos el estilo de validación
                    return;
                }
                campo.setStyle("-fx-background-color: white"); // Corrección de 'backgroudn'
            }
        });
    }
}

