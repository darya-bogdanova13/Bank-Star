package com.skypro.bank_star.service.impl;

import com.skypro.bank_star.dto.RecommendationsProductDto;
import com.skypro.bank_star.exception.ProductNotFoundException;
import com.skypro.bank_star.repository.RecommendationsRepository;
import com.skypro.bank_star.service.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final Logger logger = LoggerFactory.getLogger(RecommendationsServiceImpl.class);
    private final RecommendationsRepository recommendationsRepository;
    @Autowired
    public RecommendationsServiceImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public List<RecommendationsProductDto> getRecommendations(UUID productId) throws ProductNotFoundException {

        logger.info("Starting checking product in database for productId: {}", productId);
        if (recommendationsRepository.existsByProductId(productId)) {

            logger.info("Product {} successfully found in database and transferred to List<>", productId);
            return recommendationsRepository.findByProductId(productId)
                    .stream()
                    .map(recommendation -> {
                        RecommendationsProductDto dto = new RecommendationsProductDto();
                        dto.setProductName((String) recommendation[0]);
                        dto.setProductId((UUID) recommendation[1]);
                        dto.setProductText((String) recommendation[2]);
                        return dto;
                    }).collect(Collectors.toList());
        }
        logger.error("Error checking product in database for productId: {}", productId);
        throw new ProductNotFoundException("Product not found in database");
    }

}