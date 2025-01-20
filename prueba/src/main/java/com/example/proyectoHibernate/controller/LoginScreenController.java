package com.example.proyectoHibernate.controller;

import com.example.proyectoHibernate.dao.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
      mostrarAlerta("Exito", "Sesión iniciada correctamente");
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
