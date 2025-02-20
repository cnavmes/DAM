package com.example.proyectoHibernate.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Incidencia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "id_dispositivo", nullable = false)
  private Dispositivo dispositivo;

  private String descripcion;

  private LocalDate fecha;

  @Enumerated(EnumType.STRING)
  private TipoIncidencia tipo;

  public void setId(int id) {
    this.id = id;
  }

  public void setDispositivo(Dispositivo dispositivo) {
    this.dispositivo = dispositivo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public void setTipo(TipoIncidencia tipo) {
    this.tipo = tipo;
  }

  // Constructor vacío para Hibernate
  public Incidencia() {
  }

  public Incidencia(Dispositivo dispositivo, String descripcion, LocalDate fecha, TipoIncidencia tipo) {

    this.dispositivo = dispositivo;
    this.descripcion = descripcion;
    this.fecha = fecha;
    this.tipo = tipo;
  }

  public int getId() {
    return id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public Dispositivo getDispositivo() {
    return dispositivo;
  }

  public TipoIncidencia getTipo() {
    return tipo;
  }

}
