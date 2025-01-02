package com.skypro.bank_star.service;

import com.skypro.bank_star.dto.RecommendationsDto;
import com.skypro.bank_star.model.Recommendations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DynamicRulesService {
    Recommendations createDynamicRuleRecommendation(RecommendationsDto recommendationsDto);

    Optional<RecommendationsDto> getDynamicRuleRecommendation(UUID id);

    List<RecommendationsDto> getAllDynamicRulesRecommendations();

    void deleteDynamicRuleRecommendation(UUID id);

}