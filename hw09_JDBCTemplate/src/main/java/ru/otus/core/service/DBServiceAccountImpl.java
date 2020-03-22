package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceAccountImpl implements DBServiceAccount {
    private static Logger logger = LoggerFactory.getLogger(DBServiceAccountImpl.class);

    private final AccountDao AccountDao;

    public DBServiceAccountImpl(AccountDao AccountDao) {
        this.AccountDao = AccountDao;
    }

    @Override
    public long persistAccount(Account Account) {
        try (SessionManager sessionManager = AccountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long AccountId = AccountDao.saveAccount(Account);
                sessionManager.commitSession();

                logger.info("created Account: {}", AccountId);
                return AccountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<Account> getAccount(long id) {
        try (SessionManager sessionManager = AccountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> AccountOptional = AccountDao.findById(id);

                logger.info("Account: {}", AccountOptional.orElse(null));
                return AccountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

}
