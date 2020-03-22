package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.jdbc.template.JdbcTemplate;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final JdbcTemplate<Account> accountJdbcTemplate;
    private final SessionManager sessionManager;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor) {
        this.accountJdbcTemplate = new JdbcTemplate<>(sessionManager, dbExecutor);
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<Account> findById(long id) {
        try {
            return accountJdbcTemplate.load(id, Account.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long saveAccount(Account account) {
        try {
            return accountJdbcTemplate.create(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public long updateAccount(Account account) {
        try {
            return accountJdbcTemplate.update(account);
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
