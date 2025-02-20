package com.example.proyectoHibernate.model;

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

  public Dispositivo() {
  }

  public Dispositivo(String dispositivo) {
    this.dispositivo = dispositivo;
  }

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

  @Override
  public String toString() {
    return dispositivo;
  }
}
