import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        ConexionBD.crearTablaUsuarios();

        // Cargar el archivo FXML con un FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("res/vista.fxml"));
        Parent root = loader.load();

        //Carga el CSS
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("res/style.css").toExternalForm());

        // Obtener el controlador
        FormularioController controller = loader.getController();

        String notaGuardada = cargarNotaPersistente();
        if(notaGuardada != null){
            controller.getNotaTextField().setText(notaGuardada);
        }else{
           controller.getMensajeLabel().setText("El campo esta listo para nuevos datos");
        }

        // Configurar el título y la escena
        primaryStage.setTitle("Formulario con Base de Datos");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Evento al cerrar la aplicación
        primaryStage.setOnCloseRequest(event -> {
            if (controller.getNotaTextField().getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("¿Está seguro de que desea cerrar sin guardar una nota?");

                ButtonType confirmButton = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(confirmButton, cancelButton);

                alert.showAndWait().ifPresent(response -> {
                    if (response == cancelButton) {
                        event.consume(); // Cancela el cierre de la aplicación
                    }
                });
            }
            guardarNotaPersistente(controller.getNotaTextField().getText());
        });
    }

    //Método para cargar la nota
    private String cargarNotaPersistente() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("nota.dat"))) {
            Nota nota = (Nota) ois.readObject();
            return nota.getContenido();
        } catch (FileNotFoundException e) {
            // Archivo no encontrado, significa que no hay datos guardados
            return null;
        } catch (IOException | ClassNotFoundException e) {
            mostrarError("Error al cargar la nota: " + e.getMessage());
            return null;
        }
    }
    //Método para guardar la nota
    private void guardarNotaPersistente(String contenido) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("nota.dat"))) {
            Nota nota = new Nota(contenido);
            oos.writeObject(nota);
        } catch (IOException e) {
            mostrarError("Error al guardar la nota: " + e.getMessage());
        }
    }
    //Método para mostrar errores
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);




        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args); // Lanza la aplicación JavaFX
    }
}