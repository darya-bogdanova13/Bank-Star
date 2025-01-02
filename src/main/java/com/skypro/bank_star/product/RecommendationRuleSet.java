package com.skypro.bank_star.product;

import com.skypro.bank_star.dto.RecommendationsProductDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<List<RecommendationsProductDto>> checkRecommendation(UUID usersId);

}