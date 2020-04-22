package ru.otus.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class UsersApiController {

    private final DBServiceUser dbServiceUser;

    @Autowired
    public UsersApiController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return this.dbServiceUser.getUser(id).orElse(null);
    }

}
