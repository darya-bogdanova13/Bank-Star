package com.skypro.bank_star.controller;

import com.skypro.bank_star.dto.RecommendationsDto;
import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.repository.RecommendationsRepository;
import com.skypro.bank_star.service.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationsController.class);

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsRepository recommendationsRepository, RecommendationsService recommendationsService) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/transaction")
    public Integer getTransactionAmountForUser(@RequestParam UUID userId) {
        logger.info("Получение суммы транзакции для пользователя: {}", userId);
        return recommendationsRepository.getRandomTransactionAmount(userId);
    }

    @GetMapping("{users_id}")
    public RecommendationsDto getListOfRecommendationsForUser(@PathVariable("users_id") UUID usersId) {
        List<Recommendations> recommendations = recommendationsService.getRecommendations(usersId);
        return new RecommendationsDto(usersId.toString(), recommendations);    }
}