package ru.otus.servlet;

import com.google.gson.Gson;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class UsersApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    private static final String NAME_PARAM = "name";
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = dbServiceUser.getUser(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = dbServiceUser.saveUser(extractUserFromRequest(request));
        User user = dbServiceUser.getUser(id).orElse(null);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }

    private User extractUserFromRequest(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return new User(0,
                parameterMap.getOrDefault(NAME_PARAM, null)[0],
                parameterMap.getOrDefault(LOGIN_PARAM, null)[0],
                parameterMap.getOrDefault(PASSWORD_PARAM, null)[0]);
    }

}
