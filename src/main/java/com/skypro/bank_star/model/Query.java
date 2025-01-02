package com.skypro.bank_star.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "count", columnDefinition = "INTEGER", nullable = false)
    private Integer count;

    @OneToOne
    @JoinColumn(name = "recommendations_id", nullable = false)
    private Recommendations recommendations;

    public Query(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }
}