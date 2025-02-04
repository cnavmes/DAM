package com.example.proyectoHibernate.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.example.proyectoHibernate.dao.DispositivoDao;
import com.example.proyectoHibernate.dao.HibernateUtil;
import com.example.proyectoHibernate.dao.IncidenciaDao;
import com.example.proyectoHibernate.model.Dispositivo;
import com.example.proyectoHibernate.model.Incidencia;
import com.example.proyectoHibernate.model.TipoIncidencia;

import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AdministrarScreenController implements Initializable {

  @FXML
  private Button btnActualizar;

  @FXML
  private Button btnAgregarDisp;

  @FXML
  private Button btnCerrarSesion;

  @FXML
  private Button btnCompletar;

  @FXML
  private Button btnCrearIncidencia;

  @FXML
  private Button btnMenu;

  @FXML
  private TableColumn<Incidencia, String> colDescripcion;

  @FXML
  private TableColumn<Incidencia, String> colFecha;

  @FXML
  private TableColumn<Incidencia, Integer> colID;

  @FXML
  private TableColumn<Incidencia, Integer> colIdDispositivo;

  @FXML
  private TableColumn<Incidencia, String> colTipo;

  @FXML
  private ComboBox<Dispositivo> comboDispositivo;

  @FXML
  private ComboBox<Incidencia> comboIncidencia;

  @FXML
  private ComboBox<String> comboTipo;

  @FXML
  private DatePicker datePicker1;

  @FXML
  private TableView<Incidencia> tabla;

  @FXML
  private TextArea txtDescripcion;

  @FXML
  private TextField txtNombre;

  private ObservableList<Incidencia> incidenciasObservable = FXCollections.observableArrayList();

  private IncidenciaDao incidenciaDao;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    incidenciaDao = new IncidenciaDao();
    cargarComboDispositivo();
    cargarComboTipo();
    cargarTabla();
  }

  @FXML
  void cerrarSesionClicked(ActionEvent event) {
    try {
      EntityManager em = HibernateUtil.getInstance().getEntityManager();

      if (em.isOpen()) {
        em.close();
      }
    } catch (IllegalStateException e) {
      System.out.println("No hay EntityManager activo: " + e.getMessage());
    }
    HibernateUtil.getInstance().closeEntityManagerFactory();

    // Volver a la pantalla de login
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/com/example/proyectoHibernate/view/LoginScreen.fxml"));
      Parent root = loader.load();
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.centerOnScreen(); // Centrar la ventana
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void menuBtnClicked(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/com/example/proyectoHibernate/view/MainScreenController.fxml"));
      Parent root = loader.load();
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.centerOnScreen(); // Centrar la ventana
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void agregarDispositivoClicked(ActionEvent event) {
    String nombreDispositivo = txtNombre.getText().trim();
    if (nombreDispositivo.isEmpty()) {
      mostrarAlerta(AlertType.ERROR, "Error", "El nombre del dispositivo no puede estar vacío.");
      return;
    }

    DispositivoDao dao = new DispositivoDao();
    List<Dispositivo> dispositivos = dao.obtenerTodos();

    for (Dispositivo dispositivo : dispositivos) {
      if (dispositivo.getDispositivo().equalsIgnoreCase(nombreDispositivo)) {
        mostrarAlerta(AlertType.ERROR, "Error", "El dispositivo ya existe en la base de datos.");
        return;
      }
    }

    Dispositivo nuevoDispositivo = new Dispositivo();
    nuevoDispositivo.setDispositivo(nombreDispositivo);

    try {
      dao.guardar(nuevoDispositivo);
      mostrarAlerta(AlertType.INFORMATION, "Éxito", "Dispositivo agregado correctamente.");
      cargarComboDispositivo(); // Recargar el comboBox de dispositivos
    } catch (Exception e) {
      mostrarAlerta(AlertType.ERROR, "Error", "Hubo un problema al agregar el dispositivo.");
      e.printStackTrace();
    }
  }

  @FXML
  void crearIncidenciaClicked(ActionEvent event) {
    Dispositivo dispositivoSeleccionado = comboDispositivo.getValue();
    String tipoIncidenciaStr = comboTipo.getValue();
    String descripcion = txtDescripcion.getText().trim();
    LocalDate fecha = datePicker1.getValue();

    if (dispositivoSeleccionado == null || tipoIncidenciaStr == null || descripcion.isEmpty() || fecha == null) {
      mostrarAlerta(AlertType.ERROR, "Error", "Todos los campos deben estar rellenos.");
      return;
    }

    TipoIncidencia tipoIncidencia;
    try {
      tipoIncidencia = TipoIncidencia.valueOf(tipoIncidenciaStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      mostrarAlerta(AlertType.ERROR, "Error", "Tipo de incidencia no válido.");
      return;
    }

    Incidencia nuevaIncidencia = new Incidencia();
    nuevaIncidencia.setDispositivo(dispositivoSeleccionado);
    nuevaIncidencia.setTipo(tipoIncidencia);
    nuevaIncidencia.setDescripcion(descripcion);
    nuevaIncidencia.setFecha(fecha);

    try {
      incidenciaDao.guardar(nuevaIncidencia);
      mostrarAlerta(AlertType.INFORMATION, "Éxito", "Incidencia creada correctamente.");
      cargarTabla(); // Recargar la tabla de incidencias
    } catch (Exception e) {
      mostrarAlerta(AlertType.ERROR, "Error", "Hubo un problema al crear la incidencia.");
      e.printStackTrace();
    }
  }

  private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }

  private void cargarComboDispositivo() {
    DispositivoDao dao = new DispositivoDao();
    List<Dispositivo> dispositivos = dao.obtenerTodos();

    ObservableList<Dispositivo> dispositivosObservable = FXCollections.observableArrayList(dispositivos);

    comboDispositivo.setItems(dispositivosObservable);

    comboDispositivo.setCellFactory(param -> new ListCell<Dispositivo>() {
      @Override
      protected void updateItem(Dispositivo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
          setText(item.getDispositivo());
        } else {
          setText(null);
        }
      }
    });

    comboDispositivo.setButtonCell(new ListCell<Dispositivo>() {
      @Override
      protected void updateItem(Dispositivo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
          setText(item.getDispositivo());
        } else {
          setText(null);
        }
      }
    });

    comboDispositivo.setOnAction(event -> cargarComboIncidencia());
  }

  private void cargarComboIncidencia() {
    Dispositivo dispositivoSeleccionado = comboDispositivo.getValue();
    if (dispositivoSeleccionado != null) {
      List<Incidencia> incidencias = incidenciaDao.obtenerPorDispositivo(dispositivoSeleccionado.getId());
      ObservableList<Incidencia> incidenciasObservable = FXCollections.observableArrayList(incidencias);
      comboIncidencia.setItems(incidenciasObservable);

      comboIncidencia.setCellFactory(param -> new ListCell<Incidencia>() {
        @Override
        protected void updateItem(Incidencia item, boolean empty) {
          super.updateItem(item, empty);
          if (item != null) {
            setText(String.valueOf(item.getId()));
          } else {
            setText(null);
          }
        }
      });

      comboIncidencia.setButtonCell(new ListCell<Incidencia>() {
        @Override
        protected void updateItem(Incidencia item, boolean empty) {
          super.updateItem(item, empty);
          if (item != null) {
            setText(String.valueOf(item.getId()));
          } else {
            setText(null);
          }
        }
      });

      comboIncidencia.setOnAction(event -> actualizarCamposIncidencia());
    }
  }

  private void cargarComboTipo() {
    ObservableList<String> tipos = FXCollections.observableArrayList("LEVE", "MEDIA", "URGENTE");
    comboTipo.setItems(tipos);
  }

  private void actualizarCamposIncidencia() {
    Incidencia incidenciaSeleccionada = comboIncidencia.getValue();
    if (incidenciaSeleccionada != null) {
      if (incidenciaSeleccionada.getFecha() != null) {
        datePicker1.setValue(incidenciaSeleccionada.getFecha());
      } else {
        datePicker1.setValue(null);
      }
      comboTipo.setValue(incidenciaSeleccionada.getTipo() != null ? incidenciaSeleccionada.getTipo().toString() : null);
      txtDescripcion
          .setText(incidenciaSeleccionada.getDescripcion() != null ? incidenciaSeleccionada.getDescripcion() : "");
    } else {
      datePicker1.setValue(null);
      comboTipo.setValue(null);
      txtDescripcion.setText("");
    }
  }

  private void cargarTabla() {
    Platform.runLater(() -> {
      IncidenciaDao dao = new IncidenciaDao();
      List<Incidencia> incidencias = dao.obtenerTodos();

      if (incidencias.isEmpty()) {
        System.out.println("No hay incidencias en la base de datos.");
      }

      incidenciasObservable.setAll(incidencias); // Actualiza la lista observable
      tabla.setItems(incidenciasObservable); // Establece la lista en la tabla

      colID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
      colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
      colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(
          cellData.getValue().getFecha() != null ? cellData.getValue().getFecha().toString() : "Sin fecha"));
      colIdDispositivo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
          cellData.getValue().getDispositivo() != null ? cellData.getValue().getDispositivo().getId() : 0));
      colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(
          cellData.getValue().getTipo() != null ? cellData.getValue().getTipo().toString() : "Sin tipo"));
    });

    Rectangle clip = new Rectangle(tabla.getWidth(), tabla.getHeight());
    clip.setArcWidth(20);
    clip.setArcHeight(20);
    tabla.setClip(clip);

    tabla.widthProperty().addListener((obs, oldVal, newVal) -> clip.setWidth(newVal.doubleValue()));
    tabla.heightProperty().addListener((obs, oldVal, newVal) -> clip.setHeight(newVal.doubleValue()));
  }
}
