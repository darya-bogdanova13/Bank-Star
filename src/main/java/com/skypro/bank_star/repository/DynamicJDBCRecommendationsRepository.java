package com.skypro.bank_star.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DynamicJDBCRecommendationsRepository {

    private final Logger logger = LoggerFactory.getLogger(DynamicJDBCRecommendationsRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DynamicJDBCRecommendationsRepository(
            @Qualifier("recommendationsJdbcTemplate")
            JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserExists(UUID userId) {
        String sql = "SELECT COUNT(*) FROM USERS u WHERE u.ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        logger.info("Executing a SQL query \"isUserExists\" in the database for userId: {}", userId);
        return count != null && count > 0;
    }

    public boolean isUserOf(UUID userId, List<String> arguments) {
        String sql = "SELECT " +
                "           CASE " +
                "               WHEN " +
                "                   (SELECT COUNT(*) " +
                "                   FROM TRANSACTIONS t " +
                "                   INNER JOIN PRODUCTS p " +
                "                   ON t.PRODUCT_ID = p.ID " +
                "                   WHERE t.USER_ID = ? " +
                "                   AND p.TYPE = '" + arguments.get(0) + "') " +
                "                   >= 1 " +
                "               THEN 'true' " +
                "               ELSE 'false' " +
                "           END AS result";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId);

        logger.info("Executing a SQL query \"isUserOf\" " +
                "for userId: {} and product: {}", userId, arguments.get(0));
        return Boolean.TRUE.equals(result);
    }

    public boolean isActiveUserOf(UUID userId, List<String> arguments) {
        String sql = "SELECT " +
                "           CASE " +
                "               WHEN " +
                "                   (SELECT COUNT(*) " +
                "                   FROM TRANSACTIONS t " +
                "                   INNER JOIN PRODUCTS p " +
                "                   ON t.PRODUCT_ID = p.ID " +
                "                   WHERE t.USER_ID = ? " +
                "                   AND p.TYPE = '" + arguments.get(0) + "') " +
                "                   >= 5 " +
                "               THEN 'true' " +
                "               ELSE 'false' " +
                "           END AS result";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId);

        logger.info("Executing a SQL query \"isActiveUserOf\" " +
                "for userId: {} and product: {}", userId, arguments.get(0));
        return Boolean.TRUE.equals(result);
    }

    public boolean isTransactionSumCompare(UUID userId, List<String> arguments) {
        String sql = "SELECT " +
                "           CASE " +
                "               WHEN " +
                "                   (SELECT SUM(AMOUNT) " +
                "                   FROM TRANSACTIONS " +
                "                   WHERE PRODUCT_ID IN" +
                "                   (SELECT ID " +
                "                   FROM PRODUCTS " +
                "                   WHERE TYPE = '" + arguments.get(0) + "')" +
                "                   AND TYPE = '" + arguments.get(1) + "' " +
                "                   AND USER_ID = ?) " + arguments.get(2) + " " + arguments.get(3) + " " +
                "               THEN 'true' " +
                "               ELSE 'false' " +
                "           END AS result";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId);

        logger.info("Executing a SQL query \"isTransactionSumCompare\" " +
                "for userId: {} and product: {}", userId, arguments.get(0));
        return Boolean.TRUE.equals(result);
    }

    public boolean isTransactionSumCompareDepositWithdraw(UUID userId, List<String> arguments) {
        String sql = "SELECT " +
                "           CASE " +
                "               WHEN " +
                "                   (SELECT SUM(t.AMOUNT) " +
                "                   FROM TRANSACTIONS t" +
                "                   JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID" +
                "                   WHERE p.TYPE = '" + arguments.get(0) + "' " +
                "                   AND t.TYPE = 'DEPOSIT' " +
                "                   AND t.USER_ID = ?) " + arguments.get(1) +
                "                   (SELECT SUM(t.AMOUNT) " +
                "                   FROM TRANSACTIONS t" +
                "                   JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID" +
                "                   WHERE p.TYPE = '" + arguments.get(0) + "' " +
                "                   AND t.TYPE = 'WITHDRAW' " +
                "                   AND t.USER_ID = ?) " +
                "               THEN 'true' " +
                "               ELSE 'false' " +
                "           END AS result";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, userId);

        logger.info("Executing a SQL query \"isTransactionSumCompareDepositWithdraw\" " +
                "for userId: {} and product: {}", userId, arguments.get(0));
        return Boolean.TRUE.equals(result);
    }

}