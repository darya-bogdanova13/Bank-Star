package com.skypro.bank_star.service.impl;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.dto.UserRecommendationsDto;
import com.skypro.bank_star.exception.UserNotFoundException;
import com.skypro.bank_star.product.RecommendationRuleSet;
import com.skypro.bank_star.repository.StatsRepository;
import com.skypro.bank_star.service.UserFixedRecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserFixedRecommendationsServiceImpl implements UserFixedRecommendationsService {

    private final Logger logger = LoggerFactory.getLogger(UserFixedRecommendationsServiceImpl.class);

    private final RecommendationRuleSet[] recommendationRuleSet;
    private final StatsRepository statsRepository;

    @Autowired
    public UserFixedRecommendationsServiceImpl(RecommendationRuleSet[] recommendationRuleSet,
                                               StatsRepository statsRepository) {
        this.recommendationRuleSet = recommendationRuleSet;
        this.statsRepository = statsRepository;
    }

    @Override
    public UserRecommendationsDto getAllFixedRecommendations(UUID userId) throws UserNotFoundException {

        logger.info("Starting checking user in database for userId: {}", userId);

        if (statsRepository.isUserExists(userId)) {

            logger.info("Starting getting in List<> all recommendations for userId: {}", userId);
            List<RecommendationsProductDto> recommendations = new ArrayList<>();

            for (RecommendationRuleSet rule : recommendationRuleSet) {
                rule.checkRecommendation(userId)
                        .ifPresent(recommendations::addAll);
                logger.info("Adding result of getting recommendation to List<> for userId: {}", userId);
            }
            logger.info("Transferring all found recommendations from List<> to UserRecommendationsDTO for userId: {}", userId);
            return new UserRecommendationsDto(userId, recommendations);
        }

        logger.error("Error checking user in database for userId: {}", userId);
        throw new UserNotFoundException("User not found in database");
    }

}