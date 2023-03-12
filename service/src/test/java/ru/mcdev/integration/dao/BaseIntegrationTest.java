package ru.mcdev.integration.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.mcdev.integration.testUtils.HibernateTestUtil;
import ru.mcdev.integration.testUtils.TestDataImporter;

public abstract class BaseIntegrationTest {

    protected static SessionFactory sessionFactory;
    protected Session session;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void closeConnection() {
        sessionFactory.close();
    }

    @BeforeEach
    void beginTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void rollbackTransaction() {
        session.getTransaction().rollback();
        session.close();
    }
}
