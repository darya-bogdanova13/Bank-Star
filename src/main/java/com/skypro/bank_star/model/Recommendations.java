package com.skypro.bank_star.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recommendations")
public class Recommendations {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "product_name", columnDefinition = "TEXT", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", columnDefinition = "TEXT", nullable = false)
    private String productText;

    @JsonIgnoreProperties(value = "recommendations", allowSetters = true)
    @OneToMany(mappedBy = "recommendations", cascade = CascadeType.ALL)
    private List<DynamicRules> rule;

    @JsonIgnoreProperties (value = "recommendations", allowSetters = true)
    @OneToOne(mappedBy = "recommendations", cascade = CascadeType.ALL)
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

    public List<DynamicRules> getRule() {
        return rule;
    }

    public void setRule(List<DynamicRules> rule) {
        this.rule = rule;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

}