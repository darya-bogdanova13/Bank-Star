package com.skypro.bank_star.product.impl;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.product.RecommendationRuleSet;
import com.skypro.bank_star.repository.FixedRecommendationsRepository;
import com.skypro.bank_star.service.RecommendationsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("topSavingRule")
@RequiredArgsConstructor
public class TopSaving implements RecommendationRuleSet {

    private final UUID ID = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

    String NAME = "Top Saving";

    String PRODUCT_TYPE_DEBIT = "DEBIT";
    String PRODUCT_TYPE_SAVING = "SAVING";
    String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
    int TRANSACTION_CONDITION = 50_000;

    private final Logger logger = LoggerFactory.getLogger(TopSaving.class);

    private final FixedRecommendationsRepository fixedRecommendationsRepository;

    private final RecommendationsService recommendationsService;

    @Override
    @Cacheable(cacheNames = "fixedRecommendations", keyGenerator = "customKeyGenerator")
    public Optional<List<RecommendationsProductDto>> checkRecommendation(UUID userId) {

        logger.info("Starting checking {} recommendation for userId: {}", NAME, userId);
        if (hasDebitProduct(userId)
                && (hasDebitDepositCondition(userId) || hasSavingDepositCondition(userId))
                && hasPositiveDebitBalance(userId)
        ) {
            logger.info("Found {} recommendation for userId: {}", NAME, userId);
            return Optional.of(recommendationsService.getRecommendations(ID));
        }
        logger.info("Not Found {} recommendation for userId: {}", NAME, userId);
        return Optional.empty();
    }

    public boolean hasDebitProduct(UUID userId) {
        return fixedRecommendationsRepository.isProductExists(userId, PRODUCT_TYPE_DEBIT);
    }

    public boolean hasDebitDepositCondition(UUID userId) {
        return fixedRecommendationsRepository.getTransactionAmount(userId, PRODUCT_TYPE_DEBIT, TRANSACTION_TYPE_DEPOSIT)
                >= TRANSACTION_CONDITION;
    }

    public boolean hasSavingDepositCondition(UUID userId) {
        return fixedRecommendationsRepository.getTransactionAmount(userId, PRODUCT_TYPE_SAVING, TRANSACTION_TYPE_DEPOSIT)
                >= TRANSACTION_CONDITION;
    }

    public boolean hasPositiveDebitBalance(UUID userId) {
        return fixedRecommendationsRepository.getTransactionAmount
                (userId, PRODUCT_TYPE_DEBIT, TRANSACTION_TYPE_DEPOSIT)
                > fixedRecommendationsRepository.getTransactionAmount
                (userId, PRODUCT_TYPE_DEBIT, TRANSACTION_TYPE_WITHDRAW);
    }

}