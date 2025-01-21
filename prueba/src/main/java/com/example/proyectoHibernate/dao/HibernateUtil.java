package com.example.proyectoHibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

  private static HibernateUtil instance;
  private EntityManagerFactory entityManagerFactory;

  private HibernateUtil() {
  }

  // Método para obtener una instancia única (Singleton)
  public static HibernateUtil getInstance() {
    if (instance == null) {
      instance = new HibernateUtil();
    }
    return instance;
  }

  // Método para crear EntityManagerFactory con credenciales dinámicas
  public EntityManagerFactory createEntityManagerFactory(String username, String password) {
    if (entityManagerFactory == null) {
      Map<String, String> properties = new HashMap<>();
      properties.put("hibernate.connection.username", username);
      properties.put("hibernate.connection.password", password);
      properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3307/hibernate");
      properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

      // Crear la instancia de EntityManagerFactory
      entityManagerFactory = Persistence.createEntityManagerFactory("persistencia", properties);
    }
    return entityManagerFactory;
  }

  public boolean validarCredenciales(String username, String password) {
    try {
      createEntityManagerFactory(username, password);
      return true;
    } catch (PersistenceException e) {
      return false;
    }
  }

  // Cerrar el EntityManagerFactory
  public void closeEntityManagerFactory() {
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
      entityManagerFactory = null;
    }
  }

  // Obtener un EntityManager
  public EntityManager getEntityManager() {
    if (entityManagerFactory == null) {
      throw new IllegalStateException("EntityManagerFactory no está inicializado.");
    }
    return entityManagerFactory.createEntityManager();
  }
}