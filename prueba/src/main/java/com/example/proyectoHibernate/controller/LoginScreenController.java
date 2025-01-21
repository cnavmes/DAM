package com.example.proyectoHibernate.controller;

import com.example.proyectoHibernate.dao.HibernateUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController {

  @FXML
  private Button btnInicio;

  @FXML
  private TextField txtPassword;

  @FXML
  private TextField txtUsuario;

  @FXML
  void onBtnClick(ActionEvent event) {
    String usuario = txtUsuario.getText();
    String password = txtPassword.getText();

    txtUsuario.setText("");
    txtPassword.setText("");

    if (usuario.isEmpty() || password.isEmpty()) {
      System.out.println("Usuario o contraseña vacíos");
      return;
    }
    if (HibernateUtil.getInstance().validarCredenciales(usuario, password)) {
      try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/example/proyectoHibernate/view/MainScreenController.fxml"));
        Stage stage = (Stage) btnInicio.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.centerOnScreen();
        stage.setTitle("Gestión de incidencias");
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }

    } else {
      mostrarAlerta("Error", "Usuario o contraseña incorrectos");
    }

  }

  private void mostrarAlerta(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }
}
