package com.mateo.users_api.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.mateo.users_api.exception.BadRequestException;
import com.mateo.users_api.repository.UserRepository;

@Service
public class ValidationService {

    private final UserRepository userRepository;

    private static final Pattern RFC_PATTERN =
            Pattern.compile("^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$");

    public ValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateTaxId(String taxId) {
        if (taxId == null || !RFC_PATTERN.matcher(taxId.toUpperCase()).matches()) {
            throw new BadRequestException("Invalid tax_id format");
        }
    }

    public void validatePhone(String phone) {
        if (phone == null) {
            throw new BadRequestException("Phone is required");
        }

        String normalized = phone.replaceAll("[^0-9]", "");

        if (normalized.length() < 10 || normalized.length() > 13) {
            throw new BadRequestException("Invalid phone format");
        }

        if (normalized.length() == 12 && !normalized.startsWith("52")) {
            throw new BadRequestException("Invalid phone country code");
        }

        if (normalized.length() == 13 && !normalized.startsWith("521")) {
            throw new BadRequestException("Invalid AndresFormat phone");
        }
    }

    public void validateUniqueTaxId(String taxId) {
        boolean exists = userRepository.findAll().stream()
                .anyMatch(user -> user.getTaxId().equalsIgnoreCase(taxId));

        if (exists) {
            throw new BadRequestException("tax_id already exists");
        }
    }
}