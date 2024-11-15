package com.skypro.bank_star.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DynamicRulesRepository {

    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double getTotalDeposits(String productType, UUID userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE product_type = ? AND transaction_type = 'DEPOSIT' AND user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productType, userId}, Double.class);
    }

    public double getTotalWithdrawals(String productType, UUID userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE product_type = ? AND transaction_type = 'WITHDRAWAL' AND user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productType, userId}, Double.class);
    }

    public double getTotalTransactionSum(String productType, String transactionType, UUID userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE product_type = ? AND transaction_type = ? AND user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productType, transactionType, userId}, Double.class);
    }

    public boolean hasTransactionsOfType(String productType, UUID userId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE product_type = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{productType, userId}, Integer.class);
        return count != null && count > 0;
    }
}
