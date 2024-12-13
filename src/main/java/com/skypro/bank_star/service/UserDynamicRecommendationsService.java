package com.skypro.bank_star.service;

import com.skypro.bank_star.dto.UserRecommendationsDto;

import java.util.UUID;

public interface UserDynamicRecommendationsService {

    UserRecommendationsDto getAllDynamicRecommendations(UUID userId);

}