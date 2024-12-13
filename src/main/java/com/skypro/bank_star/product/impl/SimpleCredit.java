package com.skypro.bank_star.product.impl;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.product.RecommendationRuleSet;
import com.skypro.bank_star.repository.StatsRepository;
import com.skypro.bank_star.service.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCredit implements RecommendationRuleSet {

    private final UUID Id =UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    String Name = "Простой кредит";

    String PRODUCT_TYPE_DEBIT = "DEBIT";
    String PRODUCT_TYPE_CREDIT = "CREDIT";
    String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
    int TRANSACTION_CONDITION = 100_000;

    private final Logger logger = LoggerFactory.getLogger(SimpleCredit.class);

    private final StatsRepository statsRepository;

    private final RecommendationsService recommendationsService;

    public SimpleCredit(StatsRepository statsRepository, RecommendationsService recommendationsService) {
        this.statsRepository = statsRepository;
        this.recommendationsService = recommendationsService;
    }

    @Override
    public Optional<List<RecommendationsProductDto>> checkRecommendation(UUID userId) {

        logger.info("Starting checking {} recommendation for userId: {}", Name, userId);
        if (!hasCreditProduct(userId)
                && hasPositiveDebitBalance(userId)
                && hasDebitWithdrawCondition(userId)
        ) {
            logger.info("Found {} recommendation for userId: {}", Name, userId);
            return Optional.of(recommendationsService.getRecommendations(Id));
        }
        logger.info("Not Found {} recommendation for userId: {}", Name, userId);
        return Optional.empty();
    }

    public boolean hasCreditProduct(UUID userId) {
        return statsRepository.isProductExists(userId, PRODUCT_TYPE_CREDIT);
    }

    public boolean hasPositiveDebitBalance(UUID userId) {
        return statsRepository.getTransactionAmount
                (userId, PRODUCT_TYPE_DEBIT, TRANSACTION_TYPE_DEPOSIT)
                > statsRepository.getTransactionAmount
                (userId, PRODUCT_TYPE_DEBIT, TRANSACTION_TYPE_WITHDRAW);
    }

    public boolean hasDebitWithdrawCondition(UUID userId) {
        return statsRepository.getTransactionAmount(userId, PRODUCT_TYPE_DEBIT, TRANSACTION_TYPE_WITHDRAW)
                > TRANSACTION_CONDITION;
    }

}