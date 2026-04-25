package com.mateo.users_api.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String name;
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String taxId;
    private String createdAt;
    private List<Address> addresses;
}
