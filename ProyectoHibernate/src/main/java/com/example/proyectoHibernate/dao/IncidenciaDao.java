package com.example.proyectoHibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import com.example.proyectoHibernate.model.Incidencia;

public class IncidenciaDao {

  private HibernateUtil hibernateUtil;

  public IncidenciaDao() {
    hibernateUtil = HibernateUtil.getInstance(); // Crear instancia de HibernateUtil
  }

  // Método para obtener una incidencia por su ID
  public Incidencia obtenerPorId(int id) {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    return entityManager.find(Incidencia.class, id);
  }

  // Método para obtener todas las incidencias
  public List<Incidencia> obtenerTodos() {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    return entityManager.createQuery("FROM Incidencia", Incidencia.class).getResultList();
  }

  // Método para obtener incidencias por dispositivo
  public List<Incidencia> obtenerPorDispositivo(int dispositivoId) {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    return entityManager
        .createQuery("FROM Incidencia i WHERE i.dispositivo.id = :dispositivoId", Incidencia.class)
        .setParameter("dispositivoId", dispositivoId)
        .getResultList();
  }

  // Método para obtener incidencias filtradas por dispositivo y tipo
  public List<Incidencia> obtenerPorDispositivoYTipo(int dispositivoId, String tipoIncidencia) {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    return entityManager
        .createQuery("FROM Incidencia i WHERE i.dispositivo.id = :dispositivoId AND i.tipo = :tipoIncidencia",
            Incidencia.class)
        .setParameter("dispositivoId", dispositivoId)
        .setParameter("tipoIncidencia", tipoIncidencia)
        .getResultList();
  }

  // Método para guardar una nueva incidencia
  public void guardar(Incidencia incidencia) {
    EntityManager entityManager = hibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(incidencia);
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
    EntityManager entityManager = hibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.merge(incidencia);
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
    EntityManager entityManager = hibernateUtil.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      incidencia = entityManager.merge(incidencia); // Asegura que la incidencia esté gestionada
      entityManager.remove(incidencia);
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