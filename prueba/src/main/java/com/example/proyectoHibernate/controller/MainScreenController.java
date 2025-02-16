package com.example.proyectoHibernate.controller;

import java.util.List;

import com.example.proyectoHibernate.dao.DispositivoDao;
import com.example.proyectoHibernate.dao.HibernateUtil;
import com.example.proyectoHibernate.dao.IncidenciaDao;
import com.example.proyectoHibernate.model.Dispositivo;
import com.example.proyectoHibernate.model.Incidencia;

import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
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
  private TableView<Incidencia> tabla;

  @FXML
  private Button btnCerrarSesion;

  @FXML
  private CheckBox checkBoxLeve;

  @FXML
  private CheckBox checkBoxMedia;

  @FXML
  private CheckBox checkBoxUrgente;

  @FXML
  private ComboBox<Dispositivo> comboDispositivo;

  @FXML
  private DatePicker datePicker;

  @FXML
  private PieChart pieChart;

  @FXML
  private Button btnBorrarFiltros;

  @FXML
  private Button btnAdministrar;

  private ObservableList<Incidencia> incidenciasObservable = FXCollections.observableArrayList();

  private IncidenciaDao incidenciaDao;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    incidenciaDao = new IncidenciaDao();

    cargarCombo();
    cargarTabla();
    configurarFiltros();
    cargarDatosPieChart();

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
  void btnAdministrarClick(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/com/example/proyectoHibernate/view/AdministrarScreen.fxml"));
      Parent root = loader.load();
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.centerOnScreen();
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void cargarCombo() {

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
  }

  private void cargarTabla() {
    Platform.runLater(() -> {
      IncidenciaDao dao = new IncidenciaDao();
      List<Incidencia> incidencias = dao.obtenerTodos();

      if (incidencias.isEmpty()) {
        System.out.println("No hay incidencias en la base de datos.");
      }

      incidenciasObservable.setAll(incidencias);
      tabla.setItems(incidenciasObservable);

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

  private void configurarFiltros() {
    FilteredList<Incidencia> filteredData = new FilteredList<>(incidenciasObservable, p -> true);

    // Asociar eventos a los filtros
    checkBoxLeve.setOnAction(e -> aplicarFiltros(filteredData));
    checkBoxMedia.setOnAction(e -> aplicarFiltros(filteredData));
    checkBoxUrgente.setOnAction(e -> aplicarFiltros(filteredData));
    datePicker.setOnAction(e -> aplicarFiltros(filteredData));
    comboDispositivo.setOnAction(e -> aplicarFiltros(filteredData));

    aplicarFiltros(filteredData);

    tabla.setItems(filteredData);
  }

  private void aplicarFiltros(FilteredList<Incidencia> filteredData) {
    filteredData.setPredicate(incidencia -> {
      // Filtrar por tipo de incidencia (leve, media, urgente)
      boolean filtrarPorTipo = true;
      if (checkBoxLeve.isSelected() && !"Leve".equalsIgnoreCase(incidencia.getTipo().toString()))
        filtrarPorTipo = false;
      if (checkBoxMedia.isSelected() && !"Media".equalsIgnoreCase(incidencia.getTipo().toString()))
        filtrarPorTipo = false;
      if (checkBoxUrgente.isSelected() && !"Urgente".equalsIgnoreCase(incidencia.getTipo().toString()))
        filtrarPorTipo = false;

      if (!checkBoxLeve.isSelected() && !checkBoxMedia.isSelected() && !checkBoxUrgente.isSelected()) {
        filtrarPorTipo = true;
      }

      // Filtrar por fecha si se ha seleccionado una
      boolean filtrarPorFecha = true;
      if (datePicker.getValue() != null) {
        if (incidencia.getFecha() != null) {
          filtrarPorFecha = incidencia.getFecha().equals(datePicker.getValue());
        } else {
          filtrarPorFecha = false;
        }
      }

      // Filtrar por dispositivo si se ha seleccionado uno
      boolean filtrarPorDispositivo = true;
      if (comboDispositivo.getValue() != null) {
        filtrarPorDispositivo = incidencia.getDispositivo() != null &&
            incidencia.getDispositivo().getId() == comboDispositivo.getValue().getId();
      }

      return filtrarPorTipo && filtrarPorFecha && filtrarPorDispositivo;
    });

    // Actualizar la tabla después de aplicar los filtros
    tabla.setItems(filteredData);

    // Actualizar el PieChart después de aplicar los filtros
    actualizarPieChart(filteredData);
  }

  private void actualizarPieChart(FilteredList<Incidencia> filteredData) {
    int leveCount = 0;
    int mediaCount = 0;
    int urgenteCount = 0;

    for (Incidencia incidencia : filteredData) {
      if ("Leve".equalsIgnoreCase(incidencia.getTipo().toString())) {
        leveCount++;
      } else if ("Media".equalsIgnoreCase(incidencia.getTipo().toString())) {
        mediaCount++;
      } else if ("Urgente".equalsIgnoreCase(incidencia.getTipo().toString())) {
        urgenteCount++;
      }
    }

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("Leve", leveCount),
        new PieChart.Data("Media", mediaCount),
        new PieChart.Data("Urgente", urgenteCount));

    pieChart.setData(pieChartData);
  }

  private void cargarDatosPieChart() {
    List<Incidencia> incidencias = incidenciaDao.obtenerTodos();

    actualizarPieChart(new FilteredList<>(FXCollections.observableArrayList(incidencias), p -> true));

    Rectangle clipPie = new Rectangle(pieChart.getWidth(), pieChart.getHeight());
    clipPie.setArcWidth(20);
    clipPie.setArcHeight(20);
    pieChart.setClip(clipPie);

    pieChart.widthProperty().addListener((obs, oldVal, newVal) -> clipPie.setWidth(newVal.doubleValue()));
    pieChart.heightProperty().addListener((obs, oldVal, newVal) -> clipPie.setHeight(newVal.doubleValue()));
  }

  @FXML
  private void borrarFiltros(ActionEvent event) {
    comboDispositivo.setValue(null);
    checkBoxLeve.setSelected(false);
    checkBoxMedia.setSelected(false);
    checkBoxUrgente.setSelected(false);
    datePicker.setValue(null);
    aplicarFiltros(new FilteredList<>(incidenciasObservable, p -> true));
  }
}
