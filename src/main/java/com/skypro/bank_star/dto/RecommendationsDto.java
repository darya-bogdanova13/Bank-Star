package com.skypro.bank_star.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skypro.bank_star.model.Stats;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность рекомендации")
public class RecommendationsDto {

    @Schema(description = "Идентификатор рекомендации в БД")
    private UUID id;

    @Schema(description = "Название рекомендации")
    private String productName;

    @Schema(description = "Идентификатор рекомендации в продакшн")
    private UUID productId;

    @Schema(description = "Описание рекомендации")
    private String productText;

    @Schema(description = "Правила рекомендации")
    private List<DynamicRulesDto> rule;

    @JsonIgnore
    @Schema(description = "Счетчик срабатывания рекомендаций")
    private Stats stats;

}