package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.hibernate.HibernateUtils;

import java.util.Arrays;

public abstract class AbstractHibernateTest {
  protected static final String FIELD_ID = "id";
  protected static final String FIELD_NAME = "name";
  protected static final String TEST_USER_NAME = "Вася";
  protected static final String TEST_USER_NEW_NAME = "НЕ Вася";
  protected static final String TEST_USER_NEW_NAME2 = "Совершенно точно НЕ Вася";
  private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
  protected SessionFactory sessionFactory;

  @BeforeEach
  public void setUp() {
    sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, AddressDataSet.class, PhoneDataSet.class);
  }

  @AfterEach
  void tearDown() {
    sessionFactory.close();
  }

  protected User buildDefaultUser() {
    return new User(0, TEST_USER_NAME,
            Arrays.asList(new PhoneDataSet(0, "12332112"), new PhoneDataSet(0, "987654321")),
            new AddressDataSet(0, "ОДЫН ОДЫН ОДЫН"));
  }

  protected void saveUser(User user) {
    try (Session session = sessionFactory.openSession()) {
      saveUser(session, user);
    }
  }

  protected void saveUser(Session session, User user) {
    session.beginTransaction();
    session.save(user);
    session.getTransaction().commit();
  }

  protected User loadUser(long id) {
    try (Session session = sessionFactory.openSession()) {
      return session.find(User.class, id);
    }
  }

  protected EntityStatistics getUserStatistics() {
    Statistics stats = sessionFactory.getStatistics();
    return stats.getEntityStatistics(User.class.getName());
  }
}
