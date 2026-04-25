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
                "user1@mail.com",
                "user1",
                "+1 55 555 555 55",
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
                "AARR990101XXX",
                "01-01-2026 00:00",
                List.of(
                        new Address(1L, "workaddress", "street No. 1", "UK"),
                        new Address(2L, "homeaddress", "street No. 2", "AU"))));

        users.add(new User(
                UUID.randomUUID(),
                "user2@mail.com",
                "user2",
                "+1 55 555 555 56",
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
                "AARR990101XX1",
                "01-01-2026 00:00",
                List.of(
                        new Address(1L, "workaddress", "street No. 3", "US"),
                        new Address(2L, "homeaddress", "street No. 4", "CA")
                )
        ));

        users.add(new User(
                UUID.randomUUID(),
                "user3@mail.com",
                "user3",
                "+1 55 555 555 57",
                "7c4a8d09ca3762af61e59520943dc26494f8941b",
                "AARR990101XX2",
                "01-01-2026 00:00",
                List.of(

                        new Address(1L, "workaddress", "street No. 5", "MX"),
                        new Address(2L, "homeaddress", "street No. 6", "ES")
                )
        ));
    }
    public List<User> findAll() {
        return users;
    }

}
