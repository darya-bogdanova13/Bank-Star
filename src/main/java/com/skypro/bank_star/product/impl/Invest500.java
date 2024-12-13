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
public class Invest500 implements RecommendationRuleSet {

    private final UUID Id = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
    String Name = "Invest 500";

    String PRODUCT_TYPE_DEBIT = "DEBIT";
    String PRODUCT_TYPE_INVEST = "INVEST";
    String PRODUCT_TYPE_SAVING = "SAVING";
    String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    int TRANSACTION_CONDITION = 1000;

    private final Logger logger = LoggerFactory.getLogger(Invest500.class);

    private final StatsRepository statsRepository;
    private final RecommendationsService recommendationsService;

    public Invest500(StatsRepository statsRepository, RecommendationsService recommendationsService) {
        this.statsRepository = statsRepository;
        this.recommendationsService = recommendationsService;
    }

    @Override
    public Optional<List<RecommendationsProductDto>> checkRecommendation(UUID userId) {
        logger.info("Starting checking {} recommendation for userId: {}", Name, userId);
        if (hasDebitProduct(userId)
                && !hasInvestProduct(userId)
                && hasSavingDepositCondition(userId)
        ) {
            logger.info("Found {} recommendation for userId: {}", Name, userId);
            return Optional.of(recommendationsService.getRecommendations(Id));

        }
        logger.info("Not Found {} recommendation for userId: {}", Name, userId);
        return Optional.empty();
    }

    public boolean hasDebitProduct(UUID userId) {
        return statsRepository.isProductExists(userId, PRODUCT_TYPE_DEBIT);
    }

    public boolean hasInvestProduct(UUID userId) {
        return statsRepository.isProductExists(userId, PRODUCT_TYPE_INVEST);
    }

    public boolean hasSavingDepositCondition(UUID userId) {
        return statsRepository.getTransactionAmount(userId, PRODUCT_TYPE_SAVING, TRANSACTION_TYPE_DEPOSIT)
                > TRANSACTION_CONDITION;
    }

}
