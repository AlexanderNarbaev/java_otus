package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceAccount;
import ru.otus.core.service.DBServiceAccountImpl;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createUserTable(dataSource);
        demo.createAccountTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> userDbExecutor = new DbExecutor<>();
        DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, userDbExecutor);
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, accountDbExecutor);


        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 1));
        Optional<User> user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );
        id = dbServiceUser.saveUser(new User(0, "dbServiceUser1", 12));
        user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );
        id = dbServiceUser.saveUser(new User(0, "dbServiceUser3", 32));
        user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );


        DBServiceAccount dbServiceAccount = new DBServiceAccountImpl(accountDao);
        long idAccount = dbServiceAccount.persistAccount(new Account(0, "BANK", 1));
        Optional<Account> account = dbServiceAccount.getAccount(idAccount);
        account.ifPresentOrElse(
                crUser -> logger.info("created user, type:{}", crUser.getType()),
                () -> logger.info("user was not created")
        );
        idAccount = dbServiceAccount.persistAccount(new Account(0, "BANK2", 12));
        account = dbServiceAccount.getAccount(idAccount);
        account.ifPresentOrElse(
                crUser -> logger.info("created user, type:{}", crUser.getType()),
                () -> logger.info("user was not created")
        );
        idAccount = dbServiceAccount.persistAccount(new Account(0, "BANK4", 32));
        account = dbServiceAccount.getAccount(idAccount);
        account.ifPresentOrElse(
                crUser -> logger.info("created user, type:{}", crUser.getType()),
                () -> logger.info("user was not created")
        );

    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();

        }
        System.out.println("User table created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pst = connection.prepareStatement("create table account(no long auto_increment, type  varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("User table created");
    }
}
