package com.skypro.bank_star.service;

import com.skypro.bank_star.dto.RecommendationsProductDto;

import java.util.List;
import java.util.UUID;

public interface RecommendationsService {
    List<RecommendationsProductDto> getRecommendations(UUID productId);

}