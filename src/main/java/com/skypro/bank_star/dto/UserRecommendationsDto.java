package com.skypro.bank_star.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Сущность рекомендации, связанная с клиентом")
public record UserRecommendationsDto(@Schema(description = "Идентификатор клиента") UUID userId,
                                     @Schema(description = "Рекомендации, доступные клиенту") List<RecommendationsProductDto> recommendations) {
}