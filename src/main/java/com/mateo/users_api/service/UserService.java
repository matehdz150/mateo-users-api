package com.mateo.users_api.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mateo.users_api.dto.CreateUserRequest;
import com.mateo.users_api.dto.UpdateUserRequest;
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

    public User createUser(CreateUserRequest request) {
        User user = new User(
                UUID.randomUUID(),
                request.getEmail(),
                request.getName(),
                request.getPhone(),
                request.getPassword(),
                request.getTaxId(),
                ZonedDateTime.now(ZoneId.of("Indian/Antananarivo"))
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                request.getAddresses());

        return userRepository.save(user);
    }

    public User updateUser(UUID id, UpdateUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getPhone() != null)
            user.setPhone(request.getPhone());
        if (request.getPassword() != null)
            user.setPassword(request.getPassword());
        if (request.getTaxId() != null)
            user.setTaxId(request.getTaxId());
        if (request.getAddresses() != null)
            user.setAddresses(request.getAddresses());

        return user;
    }
}
