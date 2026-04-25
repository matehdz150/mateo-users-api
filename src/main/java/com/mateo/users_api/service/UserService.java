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

        if (sortedBy != null && !sortedBy.isBlank()) {
            users = sortUsers(users, sortedBy);
        }

        if (filter != null && !filter.isBlank()) {
            users = filterUsers(users, filter);
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

    private List<User> filterUsers(List<User> users, String filter) {
        String normalizedFilter = filter.replace(" ", "+");
        String[] parts = normalizedFilter.split("\\+", 3);

        if (parts.length != 3) {
            return users;
        }

        String field = parts[0];
        String operator = parts[1];
        String value = parts[2];

        return users.stream()
                .filter(user -> matchesFilter(user, field, operator, value))
                .toList();
    }

    private boolean matchesFilter(User user, String field, String operator, String value) {
        String fieldValue = switch (field) {
            case "email" -> user.getEmail();
            case "id" -> user.getId().toString();
            case "name" -> user.getName();
            case "phone" -> user.getPhone();
            case "tax_id" -> user.getTaxId();
            case "created_at" -> user.getCreatedAt();
            default -> null;
        };

        if (fieldValue == null) {
            return false;
        }

        return switch (operator) {
            case "co" -> fieldValue.toLowerCase().contains(value.toLowerCase());
            case "eq" -> fieldValue.equalsIgnoreCase(value);
            case "sw" -> fieldValue.toLowerCase().startsWith(value.toLowerCase());
            case "ew" -> fieldValue.toLowerCase().endsWith(value.toLowerCase());
            default -> false;
        };
    }
}
