package com.skypro.bank_star.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.model.RuleCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DynamicRulesRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public DynamicRulesRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addRule(DynamicRules rule) throws JsonProcessingException {
        String sql = "INSERT INTO dynamic_rules (id, product_name, product_id, product_text, rule) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, rule.getId(), rule.getProductName(), rule.getProductId(), rule.getProductText(),
                objectMapper.writeValueAsString(rule.getQueries()));
    }

    public List<DynamicRules> findByUserId(Long userId) {
        String sql = "SELECT * FROM dynamic_rules WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> mapRowToDynamicRules(rs));
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM dynamic_rules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<DynamicRules> findAll() {
        String sql = "SELECT * FROM dynamic_rules";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToDynamicRules(rs));
    }

    private DynamicRules mapRowToDynamicRules(ResultSet rs) throws SQLException {
        DynamicRules rule = new DynamicRules();
        rule.setId(rs.getLong("id"));
        rule.setUserId(rs.getLong("user_id"));
        rule.setProductName(rs.getString("product_name"));
        rule.setProductId(rs.getString("product_id"));
        rule.setProductText(rs.getString("product_text"));

        String rulesJson = rs.getString("rule");
        try {
            rule.setQueries(objectMapper.readValue(rulesJson, new TypeReference<List<RuleCondition>>() {
            }));
        } catch (IOException e) {

        }
        return rule;
    }

}