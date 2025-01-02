package com.skypro.bank_star.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность продукта рекомендации")
public class RecommendationsProductDto {

    @Schema(description = "Название продукта рекомендации")
    private String productName;

    @Schema(description = "Идентификатор продукта рекомендации")
    private UUID productId;

    @Schema(description = "Описание продукта рекомендации")
    private String productText;

    public String getProduct_name() {
        return null;
    }

    public UUID getProduct_id() {
        return null;
    }

    public String getProduct_text() {
        return null;
    }
}
