package com.example.proyectoHibernate.controller;

import java.util.List;

import com.example.proyectoHibernate.dao.DispositivoDao;
import com.example.proyectoHibernate.dao.HibernateUtil;
import com.example.proyectoHibernate.dao.IncidenciaDao;
import com.example.proyectoHibernate.model.Dispositivo;
import com.example.proyectoHibernate.model.Incidencia;

import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleObjectProperty;

public class MainScreenController {
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
  private Button btnCerrarSesion;

  @FXML
  private Button btnCrearIncidencia;

  @FXML
  private CheckBox checkBoxLeve;

  @FXML
  private CheckBox checkBoxMedia;

  @FXML
  private CheckBox checkBoxUrgente;

  @FXML
  private ComboBox<Dispositivo> comboDispositivo;

  public void initialize() {
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

    // Vincular columnas a las propiedades de la entidad Incidencia
    // colID.setCellValueFactory(cellData -> new
    // SimpleObjectProperty<>(cellData.getValue().getId()));
    // colDescripcion.setCellValueFactory(cellData -> new
    // SimpleStringProperty(cellData.getValue().getDescripcion()));
    // colFecha.setCellValueFactory(cellData -> new
    // SimpleObjectProperty<>(cellData.getValue().getFecha().toString()));
    // colIdDispositivo
    // .setCellValueFactory(cellData -> new
    // SimpleObjectProperty<>(cellData.getValue().getDispositivo().getId()));
    // colTipo.setCellValueFactory(cellData -> new
    // SimpleStringProperty(cellData.getValue().getTipo().name()));

  }

  @FXML
  private DatePicker datePicker;

  @FXML
  private PieChart pieChart;

  @FXML
  private TableView<Incidencia> tabla;

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
  void crearIncidenciaClicked(ActionEvent event) {
    cargarTabla();
  }

  private ObservableList<Incidencia> listaIncidencias;

  private void cargarTabla() {
    IncidenciaDao dao = new IncidenciaDao();
    List<Incidencia> incidencias = dao.obtenerTodos();
    listaIncidencias = FXCollections.observableArrayList(incidencias);
    tabla.setItems(listaIncidencias);
  }

}
