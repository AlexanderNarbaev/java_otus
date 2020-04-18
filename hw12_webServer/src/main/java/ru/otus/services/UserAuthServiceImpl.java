package ru.otus.services;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser.getUserByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
