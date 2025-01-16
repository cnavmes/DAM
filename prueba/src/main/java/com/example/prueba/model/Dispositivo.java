package com.example.prueba.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dispositivo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String dispositivo;

  // Constructor vacío para Hibernate
  public Dispositivo() {
  }

  // Constructor con parámetros
  public Dispositivo(String dispositivo) {
    this.dispositivo = dispositivo;
  }

  // Getters y setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDispositivo() {
    return dispositivo;
  }

  public void setDispositivo(String dispositivo) {
    this.dispositivo = dispositivo;
  }
}
