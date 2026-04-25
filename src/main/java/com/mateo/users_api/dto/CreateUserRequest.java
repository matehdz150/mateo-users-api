package com.mateo.users_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mateo.users_api.model.Address;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;

    private String name;

    private String phone;

    private String password;

    @JsonProperty("tax_id")

    private String taxId;

    private List<Address> addresses;
}
