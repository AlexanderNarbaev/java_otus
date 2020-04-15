package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class UserDaoHibernate implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;
    private final HwCache<Long, User> userHwCache;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
        this.userHwCache = new MyCache<>();
        this.userHwCache.addListener(new HwListener<Long, User>() {
            @Override
            public void notify(Long key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        });
    }

    @Override
    public Optional<User> findById(long id) {
        long timeMillis = System.currentTimeMillis();
        if (userHwCache.get(id) != null) {
            logger.debug("TIMER: FOUND IN CACHE. TIME:{}", System.currentTimeMillis() - timeMillis);
            return Optional.ofNullable(userHwCache.get(id));
        } else {
            DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
            try {
                User user = currentSession.getHibernateSession().find(User.class, id);
                logger.debug("TIMER: RETRIEVE FROM DB. TIME:{}", System.currentTimeMillis() - timeMillis);
                userHwCache.put(id, user);
                return Optional.ofNullable(user);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            userHwCache.put(id, null);
            return Optional.empty();
        }
    }

    @Override
    public long saveUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
