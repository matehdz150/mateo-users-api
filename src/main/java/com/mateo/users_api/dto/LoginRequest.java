package com.mateo.users_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {

    @JsonProperty("tax_id")
    private String taxId;

    private String password;
}