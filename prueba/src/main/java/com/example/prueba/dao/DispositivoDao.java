package com.example.prueba.dao;

import java.util.List;

import com.example.prueba.model.Dispositivo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DispositivoDao {

  // Método para obtener un dispositivo por su ID
  public Dispositivo obtenerPorId(int id) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    return entityManager.find(Dispositivo.class, id);
  }

  // Método para obtener todos los dispositivos
  public List<Dispositivo> obtenerTodos() {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    return entityManager.createQuery("FROM Dispositivo", Dispositivo.class).getResultList();
  }

  // Método para guardar un nuevo dispositivo
  public void guardar(Dispositivo dispositivo) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(dispositivo); // Guardar el dispositivo
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

  // Método para actualizar un dispositivo existente
  public void actualizar(Dispositivo dispositivo) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.merge(dispositivo); // Actualizar el dispositivo
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

  // Método para eliminar un dispositivo
  public void eliminar(Dispositivo dispositivo) {
    EntityManager entityManager = HibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      dispositivo = entityManager.merge(dispositivo); // Asegura que el dispositivo esté gestionado
      entityManager.remove(dispositivo); // Eliminar el dispositivo
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
