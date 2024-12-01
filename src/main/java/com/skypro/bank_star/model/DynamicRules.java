package com.skypro.bank_star.model;

import java.util.List;


public class DynamicRules {

    private long id;
    private long userId;
    private List<Query> queries;
    private String productName;
    private long productId;
    private String productText;

    public DynamicRules(long id, long userId, List<Query> queries, String productName, String productId, String productText) {
        this.id = id;
        this.userId = userId;
        this.queries = queries;
        this.productName = productName;
        this.productId = Long.parseLong(productId);
        this.productText = productText;
    }

    public DynamicRules() {
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

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

}