package com.example.proyectoHibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

  private static EntityManagerFactory entityManagerFactory;

  // Método para obtener el EntityManagerFactory
  public static EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {
      // Crear el EntityManagerFactory a partir de la configuración de persistence.xml
      entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
    }
    return entityManagerFactory;
  }

  // Método para obtener un EntityManager
  public static EntityManager getEntityManager() {
    return getEntityManagerFactory().createEntityManager();
  }

  // Método para cerrar el EntityManagerFactory
  public static void closeEntityManagerFactory() {
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
    }
  }
}
