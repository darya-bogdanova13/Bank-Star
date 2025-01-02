package com.skypro.bank_star.controller;

import com.skypro.bank_star.dto.UserRecommendationsDto;
import com.skypro.bank_star.exception.AppError;
import com.skypro.bank_star.exception.UserNotFoundException;
import com.skypro.bank_star.service.UserDynamicRecommendationsService;
import com.skypro.bank_star.service.UserFixedRecommendationsService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RuleStatisticsController {
    private final Logger logger = LoggerFactory.getLogger(RuleStatisticsController.class);
    private final UserFixedRecommendationsService userFixedRecommendationsService;
    private final UserDynamicRecommendationsService userDynamicRecommendationsService;

    public RuleStatisticsController(UserFixedRecommendationsService userFixedRecommendationsService, UserDynamicRecommendationsService userDynamicRecommendationsService) {
        this.userFixedRecommendationsService = userFixedRecommendationsService;
        this.userDynamicRecommendationsService = userDynamicRecommendationsService;
    }

    @GetMapping("/fixed/{userId}")
        public ResponseEntity<Object> getUserFixedRecommendations(@PathVariable @Parameter(description = "идентификатор клиента") UUID userId) {
            logger.info("Received request for getting all fixed relevant recommendations for userId: {}", userId);

            try {
                UserRecommendationsDto result = userFixedRecommendationsService.getAllFixedRecommendations(userId);
                logger.info("Outputting in @Controller all fixed relevant recommendations for userId: {}", userId);
                return ResponseEntity.ok(result);

            } catch (UserNotFoundException e) {

                logger.error("Error Outputting in @Controller all fixed relevant recommendations for userId: {}", userId, e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new AppError(HttpStatus.NOT_FOUND.value(),
                                "User with UUID " + userId + " not found in database"));
            }
        }

        @GetMapping(path = "/dynamic/{userId}")
        public ResponseEntity<Object> getUserDynamicRecommendations(@PathVariable @Parameter(description = "идентификатор клиента") UUID userId) {
            logger.info("Received request for getting all dynamic relevant recommendations for userId: {}", userId);

            try {
                UserRecommendationsDto result = userDynamicRecommendationsService.getAllDynamicRecommendations(userId);
                logger.info("Outputting in @Controller all dynamic relevant recommendations for userId: {}", userId);
                return ResponseEntity.ok(result);

            } catch (UserNotFoundException e) {

                logger.error("Error Outputting in @Controller all dynamic relevant recommendations for userId: {}", userId, e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new AppError(HttpStatus.NOT_FOUND.value(),
                                "User with UUID " + userId + " not found in database"));
            }
        }

    }