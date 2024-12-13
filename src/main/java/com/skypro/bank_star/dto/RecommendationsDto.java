package com.skypro.bank_star.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skypro.bank_star.model.Query;

import java.util.List;
import java.util.UUID;

public class RecommendationsDto {
    private UUID id;
    private String productName;
    private UUID productId;
    private String productText;
    private List<DynamicRulesDto> rule;
    @JsonIgnore
    private Query query;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<DynamicRulesDto> getRule() {
        return rule;
    }

    public void setRule(List<DynamicRulesDto> rule) {
        this.rule = rule;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}