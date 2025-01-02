package com.skypro.bank_star.service.impl;

import com.skypro.bank_star.model.Stats;
import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.repository.DynamicJPARecommendationsRepository;
import com.skypro.bank_star.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements StatsService {

    private final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);
    private final DynamicJPARecommendationsRepository dynamicJPARecommendationsRepository;


    public QueryServiceImpl(DynamicJPARecommendationsRepository dynamicJPARecommendationsRepository) {
        this.dynamicJPARecommendationsRepository = dynamicJPARecommendationsRepository;
    }

    @Override
    public void incrementStatsCount(UUID recommendationsId) {

        logger.info("Starting incrementing stats count for dynamic recommendations: {}", recommendationsId);

        Optional<Recommendations> foundRecommendations = dynamicJPARecommendationsRepository.findById(recommendationsId);

        if (foundRecommendations.isPresent()) {
            Recommendations recommendations = foundRecommendations.get();
            Stats stats = recommendations.getStats();

            if (stats == null) {
                stats = new Stats(1);
                stats.setRecommendations(recommendations);
                recommendations.setStats(stats);
            } else {
                stats.setCount(stats.getCount() + 1);
            }

            dynamicJPARecommendationsRepository.save(recommendations);

            logger.info("Successfully added increment stats count for dynamic recommendations: {}", recommendationsId);
        } else {
            logger.warn("Recommendations with ID {} not found.", recommendationsId);
        }
    }

    @Override
    public List<Map<String, ? extends Serializable>> getAllStatsCount() {
        logger.info("Getting all dynamic recommendations stats count");
        List<Object[]> statsList = dynamicJPARecommendationsRepository.findAllStats();

        List<Map<String, ? extends Serializable>> responseList = statsList
                .stream()
                .map(stats -> {
                    Integer count = (Integer) stats[0];
                    UUID recommendationsId = (UUID) stats[1];
                    return Map.of("recommendations_id", recommendationsId, "count", count);
                })
                .collect(Collectors.toList());

        logger.info("Successfully got {} dynamic recommendations stats count", responseList.size());
        return responseList;
    }

}