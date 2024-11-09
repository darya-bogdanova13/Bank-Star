package com.skypro.bank_star.product;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Object> getRecommendations(UUID users_id);
}
