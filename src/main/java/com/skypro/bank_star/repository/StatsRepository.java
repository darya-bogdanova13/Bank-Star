package com.skypro.bank_star.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StatsRepository {
    private final Logger logger = LoggerFactory.getLogger(StatsRepository.class);
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public StatsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
    }
    @Cacheable(cacheNames = "statsRepository", key = "#root.methodName + #userId.toString() + #productsType + #transactionType")
    public int getTransactionAmount(UUID userId, String productsType, String transactionType) {
        String sql = "SELECT SUM(t.AMOUNT) AS transactions_amount " +
                "           FROM TRANSACTIONS t " +
                "           INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "           WHERE p.TYPE = '" + productsType + "' " +
                "           AND t.TYPE = '" + transactionType + "' " +
                "           AND t.USER_ID = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        logger.info("Executing a SQL query for user transactions amount " +
                "for userId: {} with product type: {} and transaction type: {}", userId, productsType, transactionType);
        return result != null ? result : 0;
    }
    @Cacheable(cacheNames = "statsRepository", key = "#root.methodName + #userId.toString() + #productsType")
    public boolean isProductExists(UUID userId, String productsType) {
        String sql = "SELECT COUNT(*) " +
                "           FROM TRANSACTIONS t " +
                "           INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "           WHERE p.TYPE = '" + productsType + "' " +
                "           AND t.USER_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        logger.info("Executing a SQL query for the presence of a user product in the database " +
                "for userId: {} and product type: {}", userId, productsType);
        return count != null && count > 0;
    }

    @Caching(cacheable = {
            @Cacheable(cacheNames = "dynamicRecommendations", key = "#root.methodName + #userId.toString()"),
            @Cacheable(cacheNames = "statsRepository", key = "#root.methodName + #userId.toString()")})

    public boolean isUserExists(UUID userId) {
        String sql = "SELECT COUNT(*) FROM USERS u WHERE u.ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        logger.info("Executing a SQL query \"isUserExists\" in the database for userId: {}", userId);
        return count != null && count > 0;
        }

    }