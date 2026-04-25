package com.mateo.users_api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.mateo.users_api.model.Address;
import com.mateo.users_api.model.User;
import com.mateo.users_api.service.EncryptionService;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private final EncryptionService encryptionService;

    public UserRepository(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;

        users.add(new User(
                UUID.randomUUID(),
                "ana@mail.com",
                "Ana",
                "5551111111",
                encryptionService.encrypt("123456"),
                "AARR990101XXX",
                "01-01-2026 00:00",
                List.of(
                        new Address(1L, "workaddress", "street No. 1", "UK"),
                        new Address(2L, "homeaddress", "street No. 2", "AU"))));

        users.add(new User(
                UUID.randomUUID(),
                "bruno@test.com",
                "Bruno",
                "4442222222",
                encryptionService.encrypt("123456"),
                "BRRR990101XXX",
                "01-01-2026 00:00",
                List.of(
                        new Address(1L, "workaddress", "street No. 3", "US"),
                        new Address(2L, "homeaddress", "street No. 4", "CA"))));

        users.add(new User(
                UUID.randomUUID(),
                "carla@mail.com",
                "Carla",
                "5553333333",
                encryptionService.encrypt("123456"),
                "CARR990101XXX",
                "01-01-2026 00:00",
                List.of(
                        new Address(1L, "workaddress", "street No. 5", "MX"),
                        new Address(2L, "homeaddress", "street No. 6", "ES"))));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public Optional<User> findById(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public boolean deleteById(UUID id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public Optional<User> findByTaxId(String taxId) {
        return users.stream()
                .filter(user -> user.getTaxId().equalsIgnoreCase(taxId))
                .findFirst();
    }

}
