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

@Component("invest500Rule")
@RequiredArgsConstructor
public class Invest500 implements RecommendationRuleSet {

    private final UUID ID = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
    String NAME = "Invest 500";

    String PRODUCT_TYPE_DEBIT = "DEBIT";
    String PRODUCT_TYPE_INVEST = "INVEST";
    String PRODUCT_TYPE_SAVING = "SAVING";
    String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    int TRANSACTION_CONDITION = 1000;

    private final Logger logger = LoggerFactory.getLogger(Invest500.class);

    private final FixedRecommendationsRepository fixedRecommendationsRepository;
    private final RecommendationsService recommendationsService;

    @Override
    @Cacheable(cacheNames = "fixedRecommendations", keyGenerator = "customKeyGenerator")
    public Optional<List<RecommendationsProductDto>> checkRecommendation(UUID userId) {

        logger.info("Starting checking {} recommendation for userId: {}", NAME, userId);
        if (hasDebitProduct(userId)
                && !hasInvestProduct(userId)
                && hasSavingDepositCondition(userId)
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

    public boolean hasInvestProduct(UUID userId) {
        return fixedRecommendationsRepository.isProductExists(userId, PRODUCT_TYPE_INVEST);
    }

    public boolean hasSavingDepositCondition(UUID userId) {
        return fixedRecommendationsRepository.getTransactionAmount(userId, PRODUCT_TYPE_SAVING, TRANSACTION_TYPE_DEPOSIT)
                > TRANSACTION_CONDITION;
    }

}