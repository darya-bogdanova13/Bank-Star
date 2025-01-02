package com.skypro.bank_star.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skypro.bank_star.request.RulesQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "@Entity объекта запроса для правила рекомендации")
@Table(name = "rules")
public class DynamicRules {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Идентификатор объекта запроса для правила рекомендации в БД (primary key)")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Название объекта запроса для правила рекомендации")
    @Column(name = "query", nullable = false)
    private RulesQuery query;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rules_arguments", joinColumns = @JoinColumn(name = "rules_id"))
    @Schema(description = "Аргументы объекта запроса для правила рекомендации")
    @Column(name = "argument", nullable = false)
    @OrderColumn(name = "argument_index")
    private List<String> arguments;

    @Schema(description = "Соответствие объекта запроса для правила рекомендации)")
    @Column(name = "negate", nullable = false)
    private boolean negate;

    @ManyToOne
    @JoinColumn(name = "recommendation_id", nullable = false)
    private Recommendations recommendations;

}