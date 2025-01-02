package com.skypro.bank_star.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skypro.bank_star.request.RulesQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность объекта запроса для правила рекомендации")
public class DynamicRulesDto {

    @JsonIgnore
    @Schema(description = "Идентификатор объекта запроса для правила рекомендации в БД")
    private UUID id;

    @Schema(description = "Название объекта запроса для правила рекомендации")
    private RulesQuery query;

    @Schema(description = "Аргументы объекта запроса для правила рекомендации")
    private List<String> arguments;

    @Schema(description = "Соответствие объекта запроса для правила рекомендации)")
    private boolean negate;

}