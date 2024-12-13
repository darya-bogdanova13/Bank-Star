package com.skypro.bank_star.controller;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.exception.AppError;
import com.skypro.bank_star.exception.ProductNotFoundException;
import com.skypro.bank_star.service.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class RecommendationsController {

    private final Logger logger = LoggerFactory.getLogger(RecommendationsController.class);

    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable UUID productId) {

        logger.info("Received request for getting recommendation product for productId: {}", productId);

        productId = UUID.fromString(String.valueOf(productId));

        try {
            List<RecommendationsProductDto> result = recommendationsService.getRecommendations(productId);
            logger.info("Outputting in @Controller recommendation product for productId: {}", productId);
            return ResponseEntity.ok(result);

        } catch (ProductNotFoundException e) {

            logger.error("Error Outputting in @Controller recommendation product for productId: {}", productId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "Product with UUID " + productId + " not found in database"));
        }
    }

}