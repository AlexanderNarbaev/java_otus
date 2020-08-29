package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.UserDao;
import ru.otus.dao.UserDaoException;
import ru.otus.model.User;
import ru.otus.sessionmanager.DatabaseSession;
import ru.otus.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public class UserDaoHibernate implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManager sessionManager;

    public UserDaoHibernate(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(
                    currentSession.getSession().createQuery(
                            "from User u where u.login = :login",
                            User.class)
                            .setParameter("login", login)
                            .setMaxResults(1)
                            .getSingleResult());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getSession();
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

    @Override
    public Optional<List<User>> findAllUsers() {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(
                    currentSession.getSession().createQuery(
                            "from User",
                            User.class)
                            .list());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
