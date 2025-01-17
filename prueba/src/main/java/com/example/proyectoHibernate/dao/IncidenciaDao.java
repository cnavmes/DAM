package com.example.proyectoHibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

import com.example.proyectoHibernate.model.Incidencia;

public class IncidenciaDao {

  // Método para obtener una incidencia por su ID
  public Incidencia obtenerPorId(int id) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    return entityManager.find(Incidencia.class, id);
  }

  // Método para obtener todas las incidencias
  public List<Incidencia> obtenerTodos() {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    return entityManager.createQuery("FROM Incidencia", Incidencia.class).getResultList();
  }

  // Método para obtener incidencias filtradas por dispositivo y tipo
  public List<Incidencia> obtenerPorDispositivoYTipo(int dispositivoId, String tipoIncidencia) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    return entityManager
        .createQuery("FROM Incidencia i WHERE i.dispositivo.id = :dispositivoId AND i.tipo = :tipoIncidencia",
            Incidencia.class)
        .setParameter("dispositivoId", dispositivoId)
        .setParameter("tipoIncidencia", tipoIncidencia)
        .getResultList();
  }

  // Método para guardar una nueva incidencia
  public void guardar(Incidencia incidencia) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(incidencia); // Guardar la incidencia
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    } finally {
      entityManager.close();
    }
  }

  // Método para actualizar una incidencia existente
  public void actualizar(Incidencia incidencia) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.merge(incidencia); // Actualizar la incidencia
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    } finally {
      entityManager.close();
    }
  }

  // Método para eliminar una incidencia
  public void eliminar(Incidencia incidencia) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      incidencia = entityManager.merge(incidencia); // Asegura que la incidencia esté gestionada
      entityManager.remove(incidencia); // Eliminar la incidencia
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    } finally {
      entityManager.close();
    }
  }
}
