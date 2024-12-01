package com.skypro.bank_star.dto;

import java.util.List;

public class DynamicRulesDto {
    private long id;
    private long userId;
    private List<QueryDto> queries;
    private String productName;
    private String productId;
    private String productText;

    public DynamicRulesDto(Long id, Long userId, List<QueryDto> queries, String productName, long productId, String productText) {
        this.id = id;
        this.userId = userId;
        this.queries = queries;
        this.productName = productName;
        this.productId = String.valueOf(productId);
        this.productText = productText;
    }

    public DynamicRulesDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<QueryDto> getQueries() {
        return queries;
    }

    public void setQueries(List<QueryDto> queries) {
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
