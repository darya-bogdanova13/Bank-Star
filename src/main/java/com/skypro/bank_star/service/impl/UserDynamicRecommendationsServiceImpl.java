package com.skypro.bank_star.service.impl;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.dto.UserRecommendationsDto;
import com.skypro.bank_star.exception.UserNotFoundException;
import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.repository.DynamicJDBCRecommendationsRepository;
import com.skypro.bank_star.repository.DynamicJPARecommendationsRepository;
import com.skypro.bank_star.repository.RulesRecommendationsRepository;
import com.skypro.bank_star.repository.RecommendationsRepository;
import com.skypro.bank_star.service.StatsService;
import com.skypro.bank_star.service.UserDynamicRecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserDynamicRecommendationsServiceImpl implements UserDynamicRecommendationsService {

    private final Logger logger = LoggerFactory.getLogger(UserDynamicRecommendationsServiceImpl.class);

    private final DynamicJDBCRecommendationsRepository dynamicJDBCRecommendationsRepository;
    private final DynamicJPARecommendationsRepository dynamicJPARecommendationsRepository;
    private final RulesRecommendationsRepository rulesRecommendationsRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final StatsService statsService;

    public UserDynamicRecommendationsServiceImpl(DynamicJDBCRecommendationsRepository dynamicJDBCRecommendationsRepository,
                                                 DynamicJPARecommendationsRepository dynamicJPARecommendationsRepository,
                                                 RulesRecommendationsRepository rulesRecommendationsRepository,
                                                 RecommendationsRepository recommendationsRepository,
                                                 StatsService statsService) {
        this.dynamicJDBCRecommendationsRepository = dynamicJDBCRecommendationsRepository;
        this.dynamicJPARecommendationsRepository = dynamicJPARecommendationsRepository;
        this.rulesRecommendationsRepository = rulesRecommendationsRepository;
        this.recommendationsRepository = recommendationsRepository;
        this.statsService = statsService;
    }

    @Override
    @Cacheable(cacheNames = "dynamicRecommendations", key = "#root.methodName + #userId.toString()")
    public UserRecommendationsDto getAllDynamicRecommendations(UUID userId) throws UserNotFoundException {

        logger.info("Starting executing all dynamic recommendations for userId: {}", userId);

        checkIsUserExists(userId);

        List<RecommendationsProductDto> recommendations = checkUserDynamicRecommendations(userId);

        logger.info("Transferring all found recommendations from List<> to UserRecommendationsDTO for userId: {}", userId);
        return new UserRecommendationsDto(userId, recommendations);

    }
    @Override
    @Cacheable(cacheNames = "telegramBot", key = "#root.methodName + #userId.toString()")
    public String getAllDynamicRulesRecommendationsForTelegramBot(UUID userId) {

        logger.info("Starting executing all dynamic recommendations fot TelegramBot for userId: {}", userId);

        List<RecommendationsProductDto> recommendations = checkUserDynamicRecommendations(userId);

        StringBuilder sb = new StringBuilder();

        for (RecommendationsProductDto recommendation : recommendations) {
            sb.append("*Название продукта:* ").append(recommendation.getProductName()).append("\n\n");
            sb.append("*Описание продукта:* \n").append(recommendation.getProductText()).append("\n\n\n");
        }
        logger.info("Forwarding all dynamic recommendations in String for TelegramBot for userId: {}", userId);
        return sb.toString();
    }

    private void checkIsUserExists(UUID userId) throws UserNotFoundException, NullPointerException {

        logger.info("Starting checking user in database for userId: {}", userId);
        boolean flag = dynamicJDBCRecommendationsRepository.isUserExists(userId);

        if (userId == null) {
            logger.error("Error (NullPointer) checking user in database for userId");
            throw new NullPointerException("User is null");

        } else if (!flag) {
            logger.error("Error (UserNotFound) checking user in database for userId: {}", userId);
            throw new UserNotFoundException("User not found in database");
        }

        logger.info("User with id {} successfully exists", userId);
    }

    private List<RecommendationsProductDto> checkUserDynamicRecommendations(UUID userId) {

        List<RecommendationsProductDto> recommendations = new ArrayList<>();
        List<UUID> foundAllRecommendationsInDB = dynamicJPARecommendationsRepository.findAllRecommendationsIDs();
        logger.info("List's size of checking dynamic recommendations: {}", foundAllRecommendationsInDB.size());

        for (UUID recommendationId : foundAllRecommendationsInDB) {

            logger.info("Start checking rules of dynamic recommendation: {}", recommendationId);

            boolean allCasesMatched = true;
            List<DynamicRules> checkingRecommendationsRules = rulesRecommendationsRepository
                    .findByRecommendationsId(recommendationId);

            logger.info("Number of rules - {} - for checking of dynamic recommendation: {}", checkingRecommendationsRules.size(), recommendationId);

            for (DynamicRules dynamicRules : checkingRecommendationsRules) {
                boolean result = switch (dynamicRules.getQuery()) {
                    case USER_OF -> dynamicJDBCRecommendationsRepository
                            .isUserOf(userId, dynamicRules.getArguments());
                    case ACTIVE_USER_OF -> dynamicJDBCRecommendationsRepository
                            .isActiveUserOf(userId, dynamicRules.getArguments());
                    case TRANSACTION_SUM_COMPARE -> dynamicJDBCRecommendationsRepository
                            .isTransactionSumCompare(userId, dynamicRules.getArguments());
                    case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> dynamicJDBCRecommendationsRepository
                            .isTransactionSumCompareDepositWithdraw(userId, dynamicRules.getArguments());
                };

                if (result != dynamicRules.isNegate()) {
                    allCasesMatched = false;
                    logger.warn("Rule {} did not match for recommendationId - {}", dynamicRules.getQuery(), recommendationId);
                    break;
                }
            }

            if (allCasesMatched) {

                statsService.incrementStatsCount(recommendationId);

                logger.info("Adding result of getting recommendation {} to List<> for userId: {}", recommendationId, userId);
                List<RecommendationsProductDto> matchedRecommendations = recommendationsRepository.findById(recommendationId)
                        .stream()
                        .map(recommendation -> {
                            RecommendationsProductDto dto = new RecommendationsProductDto();
                            dto.setProductName(recommendation.getProductName());
                            dto.setProductId(recommendation.getProductId());
                            dto.setProductText(recommendation.getProductText());
                            return dto;
                        }).toList();

                recommendations.addAll(matchedRecommendations);
                logger.info("Recommendations successfully added for user: {}", userId);
            }
        }
        return recommendations;
    }

}