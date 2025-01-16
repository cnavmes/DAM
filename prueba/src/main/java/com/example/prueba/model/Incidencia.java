package com.example.prueba.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Incidencia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "id_dispositivo", nullable = false)
  private Dispositivo dispositivo;

  private String descripcion;

  @Temporal(TemporalType.DATE)
  private Date fecha;

  @Enumerated(EnumType.STRING)
  private TipoIncidencia tipo;

  // Constructor vacío para Hibernate
  public Incidencia() {
  }

  // Constructor con parámetros
  public Incidencia(Dispositivo dispositivo, String descripcion, Date fecha, TipoIncidencia tipo) {
    this.dispositivo = dispositivo;
    this.descripcion = descripcion;
    this.fecha = fecha;
    this.tipo = tipo;
  }

  // Getters y setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Dispositivo getDispositivo() {
    return dispositivo;
  }

  public void setDispositivo(Dispositivo dispositivo) {
    this.dispositivo = dispositivo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public TipoIncidencia getTipo() {
    return tipo;
  }

  public void setTipo(TipoIncidencia tipo) {
    this.tipo = tipo;
  }
}
