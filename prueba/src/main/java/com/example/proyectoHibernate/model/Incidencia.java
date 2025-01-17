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

  private LocalDate fecha;

  @Enumerated(EnumType.STRING)
  private TipoIncidencia tipo;

  // Constructor vacío para Hibernate
  public Incidencia() {
  }

  public Incidencia(Dispositivo dispositivo, String descripcion, LocalDate fecha, TipoIncidencia tipo) {

    this.dispositivo = dispositivo;
    this.descripcion = descripcion;
    this.fecha = fecha;
    this.tipo = tipo;
  }

}
