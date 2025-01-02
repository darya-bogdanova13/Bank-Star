package com.skypro.bank_star.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Сущность клиента")
public record UserDto(@Schema(description = "Идентификатор клиента в БД") UUID id,
                      @Schema(description = "Псевдоним клиента в БД") String userName,
                      @Schema(description = "Имя клиента в БД") String firstName,
                      @Schema(description = "Фамилия клиента в БД") String lastName) {
}