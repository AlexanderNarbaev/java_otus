package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.*;

/**
 * При параметрах -Xmx4G -Xms4G - чаще находится объект в кеше, и быстрее чем в БД
 * При параметрах -Xmx128m -Xms128m -в кеше объект находится намного реже, скорость из кеша - быстрее чем из БД
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager, new MyCache<>());
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        List<Long> savedUsersId = new ArrayList<>();
        int i = 0;
        while (i < 10_000) {
            long id = dbServiceUser.saveUser(new User(0, "Вася",
                    Arrays.asList(new PhoneDataSet(0, "12332112")),
                    new AddressDataSet(0, "ОДЫН ОДЫН ОДЫН")));
            savedUsersId.add(id);
            i++;
        }
        i = 0;

        while (i < 100_000) {
            Optional<User> mayBeCreatedUser = dbServiceUser.getUser(new Random().nextInt(10_000));
            outputUserOptional("Created user", mayBeCreatedUser);
            i++;
        }

        long id = dbServiceUser.saveUser(new User(1L, "А! Нет. Это же совсем не Вася",
                Arrays.asList(new PhoneDataSet(0, "7898787897899")),
                new AddressDataSet(0, "ДВА ОДЫН НОЛ")));
        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);


        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
        System.out.println("-----------------------------------------------------------");
    }
}
