package com.skypro.bank_star.dto;

import java.util.UUID;

public record UserDto(UUID id, String userName, String firstName, String lastName) {
    @Override
    public UUID id() {
        return id;
    }
}