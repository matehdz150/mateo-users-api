package com.mateo.users_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;

    private String name;

    private String street;

    @JsonProperty("country_code")

    private String countryCode;
}
