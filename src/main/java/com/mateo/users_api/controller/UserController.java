package com.mateo.users_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mateo.users_api.model.User;
import com.mateo.users_api.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(
        @RequestParam(required = false) String sortedBy,
        @RequestParam(required = false) String filter
    ) {
        return userService.getUsers(sortedBy, filter);
    }
}
