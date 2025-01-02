package com.skypro.bank_star.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RecommendationQueryService {

    void incrementStatsCount(UUID recommendationsId);

    List<Map<String, ? extends Serializable>> getAllStatsCount();

}


