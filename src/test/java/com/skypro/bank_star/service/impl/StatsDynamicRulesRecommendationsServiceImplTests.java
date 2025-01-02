package com.skypro.bank_star.service.impl;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.dto.UserRecommendationsDto;
import com.skypro.bank_star.exception.UserNotFoundException;
import com.skypro.bank_star.product.RecommendationRuleSet;
import com.skypro.bank_star.repository.FixedRecommendationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StatsDynamicRulesRecommendationsServiceImplTests {

    private UserFixedRecommendationsServiceImpl recommendationService;

    @Mock
    private RecommendationRuleSet ruleSetMock;

    @Mock
    private FixedRecommendationsRepository fixedRecommendationsRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendationService = new UserFixedRecommendationsServiceImpl(new RecommendationRuleSet[]{ruleSetMock}, fixedRecommendationsRepositoryMock);
    }

    @Test
    void testGetAllRecommendations_WithRecommendations() {
        UUID userId = UUID.randomUUID();
        RecommendationsProductDto recommendation = new RecommendationsProductDto(
                "Инвестиции", UUID.randomUUID(), "Инвестируйте 500");

        List<RecommendationsProductDto> recommendationsList = Collections.singletonList(recommendation);

        when(fixedRecommendationsRepositoryMock.isUserExists(userId)).thenReturn(true);

        when(ruleSetMock.checkRecommendation(userId))
                .thenReturn(Optional.of(recommendationsList));

        UserRecommendationsDto result = recommendationService.getAllFixedRecommendations(userId);

        assertEquals(1, result.recommendations().size());
        assertEquals(userId, result.userId());
        assertEquals("Инвестиции", result.recommendations().get(0).getProduct_name());
    }

    @Test
    void testGetAllRecommendations_WithoutRecommendations() {
        UUID userId = UUID.randomUUID();

        when(fixedRecommendationsRepositoryMock.isUserExists(userId)).thenReturn(true);

        when(ruleSetMock.checkRecommendation(userId))
                .thenReturn(Optional.empty());

        UserRecommendationsDto result = recommendationService.getAllFixedRecommendations(userId);

        assertEquals(0, result.recommendations().size());
        assertEquals(userId, result.userId());
    }

    @Test
    void testGetAllRecommendations_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(fixedRecommendationsRepositoryMock.isUserExists(userId)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            recommendationService.getAllFixedRecommendations(userId);
        });

        assertEquals("User not found in database", exception.getMessage());
    }
}