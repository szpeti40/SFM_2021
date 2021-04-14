package org.hotelroom.model;

import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.h2.tools.Server;

public class Application {

    public static void main(String[] args) throws SQLException {
        startDatabase();

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        Receptionist receptionist = new Receptionist();
        receptionist.setFirstName("admin");
        receptionist.setLastName("admin");
        receptionist.setU_name("admin");
        receptionist.setPassword("admin");

        Rooms rooms1 = new Rooms();
        rooms1.setBedType("doublebed");
        rooms1.setBalcony(true);
        rooms1.setReceptionist(1);
        rooms1.setPrice(10000);


        entityManager.getTransaction().begin();
        entityManager.persist(receptionist);
        entityManager.persist(rooms1);
        entityManager.getTransaction().commit();

        System.out.println("Open your browser and navigate to http://localhost:8082/");
        System.out.println("JDBC URL: jdbc:h2:mem:my_database");
        System.out.println("User Name: sa");
        System.out.println("Password: ");

    }

    private static void startDatabase() throws SQLException {
        new Server().runTool("-tcp", "-web", "-ifNotExists");
    }
}
