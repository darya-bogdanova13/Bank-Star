package com.skypro.bank_star.controller;

import com.skypro.bank_star.dto.RecommendationsDto;
import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.exception.AppError;
import com.skypro.bank_star.exception.NullOrEmptyException;
import com.skypro.bank_star.exception.RuleNotFoundException;
import com.skypro.bank_star.service.DynamicRulesService;
import com.skypro.bank_star.service.RecommendationQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rules")
public class DynamicRulesController {
    private final Logger logger = LoggerFactory.getLogger(DynamicRulesController.class);
    private final DynamicRulesService dynamicRulesService;
    private final RecommendationQueryService recommendationQueryService;

    public DynamicRulesController(DynamicRulesService dynamicRulesService, RecommendationQueryService recommendationQueryService) {
        this.dynamicRulesService = dynamicRulesService;
        this.recommendationQueryService = recommendationQueryService;
    }

    @PostMapping
    public ResponseEntity<Object> createDynamicRecommendation(@Parameter(description = "Динамическая рекомендация") @RequestBody RecommendationsDto recommendations) {
        logger.info("Received request for creating dynamic rule recommendation: {}", recommendations);

        try {
            Recommendations createdRecommendation = dynamicRulesService.createDynamicRuleRecommendation(recommendations);
            logger.info("Successfully created dynamic rule recommendation: {}", recommendations);
            return ResponseEntity.ok(createdRecommendation);

        } catch (IllegalArgumentException | NullOrEmptyException e) {

            logger.error("Error creating dynamic rule recommendation: {}", recommendations, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new AppError(HttpStatus.BAD_REQUEST.value(),
                            "Dynamic rule recommendation cannot be created due to an Exception"));
        }
    }

    @GetMapping("/{ruleId}")
    public ResponseEntity<Object> getDynamicRecommendation(@Parameter(description = "Id динамической рекомендации")@PathVariable UUID ruleId) {

        logger.info("Received request for getting dynamic rule recommendation for ruleId: {}", ruleId);
        try {
            Optional<RecommendationsDto> foundRecommendation = dynamicRulesService.getDynamicRuleRecommendation(ruleId);
            logger.info("Outputting in @Controller dynamic rule recommendation for ruleId: {}", ruleId);
            return ResponseEntity.ok(foundRecommendation);

        } catch (RuleNotFoundException e) {

            logger.error("Error Outputting in @Controller dynamic rule recommendation for ruleId: {}", ruleId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "Dynamic rule recommendation with UUID " + ruleId + " not found in database"));
        }
    }

    @GetMapping("/allRules")
    public ResponseEntity<Object> getAllDynamicRecommendations() {
        logger.info("Received request for getting all dynamic rules recommendations");

        try {
            List<RecommendationsDto> foundAllRecommendations = dynamicRulesService.getAllDynamicRulesRecommendations();
            logger.info("Outputting in @Controller all dynamic rules recommendations");
            return ResponseEntity.ok(foundAllRecommendations);

        } catch (RuleNotFoundException e) {

            logger.error("Error Outputting in @Controller all dynamic rules recommendations", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "No Dynamic rule recommendation was found in database"));
        }
    }

    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Object> deleteDynamicRecommendation(@PathVariable UUID ruleId) {
        logger.info("Received request for deleting dynamic rule recommendation for ruleId: {}", ruleId);

        try {
            dynamicRulesService.deleteDynamicRuleRecommendation(ruleId);
            logger.info("Successfully deleted dynamic rule recommendation for ruleId: {}", ruleId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new AppError(HttpStatus.NO_CONTENT.value(),
                            "No Content"));

        } catch (RuleNotFoundException e) {

            logger.error("Error deleting dynamic rule recommendation for ruleId: {}", ruleId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "Dynamic rule recommendation with UUID " + ruleId + " not found in database"));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<Map<String, ? extends Serializable>>>> getAllStatsCount() {
        logger.info("Receiving a request for recommendations counter stats");

        List<Map<String, ? extends Serializable>> mappingStatsCount = recommendationQueryService.getAllStatsCount();

        logger.info("Outputting in @Controller recommendations counter stats");
        return ResponseEntity.ok(Map.of("stats", mappingStatsCount));
    }

}