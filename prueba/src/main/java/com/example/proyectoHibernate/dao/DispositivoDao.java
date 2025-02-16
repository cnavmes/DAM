package com.example.proyectoHibernate.dao;

import java.util.List;
import com.example.proyectoHibernate.model.Dispositivo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DispositivoDao {

  private HibernateUtil hibernateUtil;

  public DispositivoDao() {
    hibernateUtil = HibernateUtil.getInstance();
  }

  // Método para obtener un dispositivo por su ID
  public Dispositivo obtenerPorId(int id) {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    return entityManager.find(Dispositivo.class, id);
  }

  // Método para obtener todos los dispositivos
  public List<Dispositivo> obtenerTodos() {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    return entityManager.createQuery("FROM Dispositivo", Dispositivo.class).getResultList();
  }

  // Método para guardar un nuevo dispositivo
  public void guardar(Dispositivo dispositivo) {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(dispositivo);
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
    EntityManager entityManager = hibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.merge(dispositivo);
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
    EntityManager entityManager = hibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      dispositivo = entityManager.merge(dispositivo);
      entityManager.remove(dispositivo);
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