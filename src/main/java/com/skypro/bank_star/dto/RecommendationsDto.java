package com.skypro.bank_star.dto;

import com.skypro.bank_star.model.Recommendations;

import java.util.List;

public class RecommendationsDto {
    private String userId;
    private List<Recommendations> recommendations;

    public RecommendationsDto(String userId, List<Recommendations> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }


    public List<Recommendations> getRecommendations() {
        return recommendations;

    }

    public String getUserId() {
        return userId;
    }
}