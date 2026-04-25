package com.mateo.users_api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.mateo.users_api.model.Address;
import com.mateo.users_api.model.User;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {

        users.add(new User(
                UUID.randomUUID(),
                "ana@mail.com",
                "Ana",
                "5551111111",
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
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
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
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
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
                "CARR990101XXX",
                "01-01-2026 00:00",
                List.of(
                        new Address(1L, "workaddress", "street No. 5", "MX"),
                        new Address(2L, "homeaddress", "street No. 6", "ES"))));
    }

    public List<User> findAll() {
        return users;
    }

}
