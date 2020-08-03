package ru.otus.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class UserRestController {

    private final DBServiceUser usersService;

    public UserRestController(DBServiceUser usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/api/user/{id}")
    public User getUserById(@PathVariable(name = "id") long id) {
        return usersService.getUser(id).orElse(null);
    }

    @GetMapping("/api/user")
    public User getUserByName(@RequestParam(name = "login") String name) {
        return usersService.getUserByLogin(name).orElse(null);
    }

    @PostMapping("/api/user")
    public User saveUser(@RequestBody User user) {
        return usersService.getUser(usersService.saveUser(user)).orElse(null);
    }

    @GetMapping("/api/user/random")
    public User findRandom() {
        return usersService.getUsers().map(userList -> userList.get(ThreadLocalRandom.current().nextInt(0, userList.size()))).orElse(null);
    }

}