package com.skypro.bank_star.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skypro.bank_star.model.RuleCondition;

import java.util.List;

public class DynamicRulesDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("queries")
    private List<RuleCondition> queries;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("productText")
    private String productText;

    public DynamicRulesDto(Long id, Long userId, List<RuleCondition> queries, String productName, String productId, String productText)  {
        this.id = id;
        this.userId = userId;
        this.queries = queries;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
    }

    public DynamicRulesDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<RuleCondition> getQueries() {
        return queries;
    }

    public void setQueries(List<RuleCondition> queries) {
        this.queries = queries;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }
}