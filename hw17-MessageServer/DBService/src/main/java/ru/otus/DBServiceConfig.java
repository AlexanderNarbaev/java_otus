package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.dao.UserDao;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;
import ru.otus.services.DbServiceUserImpl;
import ru.otus.sessionmanager.SessionManager;

@Configuration
public class DBServiceConfig {

    @Bean
    public DBServiceUser getDBServiceUser(UserDao userDao, HwCache<Long, User> userHwCache) {
        return new DbServiceUserImpl(userDao, userHwCache);
    }

    @Bean
    public UserDao getUserDao(SessionManager sessionManager) {
        return new UserDaoHibernate(sessionManager);
    }

    @Bean
    public SessionManager geSessionManager(SessionFactory sessionFactory) {
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public HwCache<Long, User> geHwCache() {
        return new MyCache<Long, User>();
    }
}
