package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.services.UserAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Controller
public class LoginController extends HttpServlet {

    private final UserAuthService userAuthService;

    @Autowired
    public LoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        System.out.println("TEST");
        return "adminLogin.html";
    }

    @PostMapping("/login/action")
    public RedirectView userSave(@ModelAttribute String login, @ModelAttribute String password) {
        RedirectView redirectView;
        if (userAuthService.authenticate(login, password)) {
            redirectView = new RedirectView("/user/list", true);
        } else {
            redirectView = new RedirectView("/login", true);
            redirectView.setStatusCode(HttpStatus.UNAUTHORIZED);
        }
        return redirectView;
    }
}
