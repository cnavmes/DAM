package com.example.prueba;

import java.util.Date;

import com.example.prueba.dao.DispositivoDao;
import com.example.prueba.dao.HibernateUtil;
import com.example.prueba.dao.IncidenciaDao;
import com.example.prueba.model.Dispositivo;
import com.example.prueba.model.Incidencia;
import com.example.prueba.model.TipoIncidencia;

import jakarta.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {

        // Crear tablas automáticamente (si aún no existen) al iniciar Hibernate
        // Configurado con hibernate.hbm2ddl.auto=update en el archivo persistence.xml o
        // hibernate.cfg.xml

        // Crear una instancia de EntityManager
        EntityManager entityManager = HibernateUtil.getEntityManager();

        // Crear algunas instancias de Dispositivo
        Dispositivo dispositivo1 = new Dispositivo("Dispositivo A");
        Dispositivo dispositivo2 = new Dispositivo("Dispositivo B");

        // Guardar Dispositivos en la base de datos
        DispositivoDao dispositivoDAO = new DispositivoDao();
        dispositivoDAO.guardar(dispositivo1);
        dispositivoDAO.guardar(dispositivo2);

        // Crear algunas incidencias
        Incidencia incidencia1 = new Incidencia(dispositivo1, "Incidencia 1 en A", new Date(), TipoIncidencia.LEVE);
        Incidencia incidencia2 = new Incidencia(dispositivo1, "Incidencia 2 en A", new Date(), TipoIncidencia.URGENTE);
        Incidencia incidencia3 = new Incidencia(dispositivo2, "Incidencia 1 en B", new Date(), TipoIncidencia.MEDIA);

        // Guardar Incidencias en la base de datos
        IncidenciaDao incidenciaDAO = new IncidenciaDao();
        incidenciaDAO.guardar(incidencia1);
        incidenciaDAO.guardar(incidencia2);
        incidenciaDAO.guardar(incidencia3);

        // Confirmar que se ha guardado correctamente
        System.out.println("Tablas creadas y datos insertados.");

        // Cerrar el EntityManager
        entityManager.close();
    }
}
