package com.skypro.bank_star.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skypro.bank_star.request.RulesQuery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rules")
public class DynamicRules {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "query", nullable = false)
    private RulesQuery query;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rules_arguments", joinColumns = @JoinColumn(name = "rules_id"))
    @Column(name = "argument", nullable = false)
    @OrderColumn(name = "argument_index")
    private List<String> arguments;

    @Column(name = "argument", nullable = false)
    private boolean negate;

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private Recommendations recommendations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RulesQuery getQuery() {
        return query;
    }

    public void setQuery(RulesQuery query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public Recommendations getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

}