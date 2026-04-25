package com.mateo.users_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mateo.users_api.model.User;
import com.mateo.users_api.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(String sortedBy, String filter) {
        List<User> users = userRepository.findAll();

        if(sortedBy != null && !sortedBy.isBlank()){
            users = sortUsers(users, sortedBy);
        }
        return users;
    }

    private List<User> sortUsers(List<User> users, String sortedBy) {
    return switch (sortedBy) {
        case "email" -> users.stream()
                .sorted((a, b) -> a.getEmail().compareToIgnoreCase(b.getEmail()))
                .toList();

        case "id" -> users.stream()
                .sorted((a, b) -> a.getId().toString().compareToIgnoreCase(b.getId().toString()))
                .toList();

        case "name" -> users.stream()
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                .toList();

        case "phone" -> users.stream()
                .sorted((a, b) -> a.getPhone().compareToIgnoreCase(b.getPhone()))
                .toList();

        case "tax_id" -> users.stream()
                .sorted((a, b) -> a.getTaxId().compareToIgnoreCase(b.getTaxId()))
                .toList();

        case "created_at" -> users.stream()
                .sorted((a, b) -> a.getCreatedAt().compareToIgnoreCase(b.getCreatedAt()))
                .toList();

        default -> users;
    };
}
}
