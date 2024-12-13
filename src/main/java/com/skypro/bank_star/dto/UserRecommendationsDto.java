package com.skypro.bank_star.dto;

import java.util.List;
import java.util.UUID;

public record UserRecommendationsDto(UUID userId,List<RecommendationsProductDto> recommendations) {
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
