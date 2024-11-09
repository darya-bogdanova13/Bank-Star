package com.skypro.bank_star.service;

import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.product.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationsService {

    private final List<RecommendationRuleSet> recommendationRuleSetList;

    public RecommendationsService(List<RecommendationRuleSet> recommendationRuleSetList) {
        this.recommendationRuleSetList = recommendationRuleSetList;
    }


    public List<Recommendations> getRecommendations(UUID users_id) {
        List<Recommendations> recommendationsList = new ArrayList<>();
        for (RecommendationRuleSet recommendationsRuleSet : recommendationRuleSetList) {
            Optional<Object> recommendations = recommendationsRuleSet.getRecommendations(users_id);
            if (recommendations.isPresent()) {
                recommendationsList.add((Recommendations) recommendations.get());
            }
        }
        return recommendationsList;
    }
}