package com.skypro.bank_star.repository;

import com.skypro.bank_star.model.Recommendations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RecommendationsRepository extends JpaRepository<Recommendations, UUID> {

    @Query(value = "SELECT r.product_name, r.product_id, r.product_text " +
            "           FROM recommendations r " +
            "           WHERE r.product_id = :productId", nativeQuery = true)
    List<Object[]> findByProductId(@Param("productId") UUID productId);

    @Query(value = "SELECT " +
            "           CASE " +
            "               WHEN COUNT(r) > 0 " +
            "               THEN true " +
            "               ELSE false " +
            "           END " +
            "       FROM recommendations r " +
            "       WHERE r.product_id = :productId", nativeQuery = true)
    boolean existsByProductId(@Param("productId") UUID productId);

}