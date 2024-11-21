package com.skypro.bank_star.repository;

import com.skypro.bank_star.model.DynamicRules;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DynamicRulesRepository {
    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(DynamicRules rule) {
        String sql = "INSERT INTO dynamic_rules (id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, rule.getId(), rule.getUserId());
    }

    public List<DynamicRules> findByUserId(Long userId) {
        String sql = "SELECT * FROM dynamic_rules WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            DynamicRules rule = new DynamicRules();
            rule.setId(rs.getLong("id"));
            rule.setUserId(rs.getLong("user_id"));
            return rule;
        });
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM dynamic_rules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<DynamicRules> findAll() {
        String sql = "SELECT * FROM dynamic_rules";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DynamicRules rule = new DynamicRules();
            rule.setId(rs.getLong("id"));
            rule.setUserId(rs.getLong("user_id"));
            return null;
        });
    }
}