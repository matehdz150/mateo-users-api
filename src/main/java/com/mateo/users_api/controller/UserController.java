package com.mateo.users_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mateo.users_api.dto.CreateUserRequest;
import com.mateo.users_api.dto.LoginRequest;
import com.mateo.users_api.dto.UpdateUserRequest;
import com.mateo.users_api.model.User;
import com.mateo.users_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Users management API")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns all users with optional sorting and filtering")
    @GetMapping("/users")
    public List<User> getUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter) {
        return userService.getUsers(sortedBy, filter);
    }

    @Operation(summary = "Create user", description = "Creates a new user")
    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Update user", description = "Updates an existing user by id")
    @PatchMapping("/users/{id}")
    public User updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by id")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Login user", description = "Authenticate user with tax_id and password")
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
