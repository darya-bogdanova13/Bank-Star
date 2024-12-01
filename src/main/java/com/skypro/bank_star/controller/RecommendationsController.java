package com.skypro.bank_star.controller;

import com.skypro.bank_star.dto.RecommendationsDto;
import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.repository.RecommendationsRepository;
import com.skypro.bank_star.service.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> getTransactionAmountForUser(@RequestParam UUID userId) {
        logger.info("Получение суммы транзакции для пользователя: {}", userId);
        Integer transactionAmount = recommendationsRepository.getRandomTransactionAmount(userId);
        return ResponseEntity.ok(transactionAmount);
    }

    @PostMapping("/users/{usersId}")
    public ResponseEntity<RecommendationsDto>  getListOfRecommendationsForUser(@PathVariable("users_id") UUID usersId) {
        logger.info("Получение рекомендаций для пользователя: {}", usersId);
        List<Recommendations> recommendations = recommendationsService.getRecommendations(usersId);
        RecommendationsDto recommendationsDto = new RecommendationsDto(usersId.toString(), recommendations);
        return ResponseEntity.ok(recommendationsDto);
    }

}