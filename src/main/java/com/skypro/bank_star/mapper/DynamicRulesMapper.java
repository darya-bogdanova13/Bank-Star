package com.skypro.bank_star.mapper;

import com.skypro.bank_star.dto.DynamicRulesDto;
import com.skypro.bank_star.dto.QueryDto;
import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.model.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicRulesMapper {
    @Value("${server.port}")
    private int port;
    public DynamicRulesDto toDto(DynamicRules dynamicRules) {
        DynamicRulesDto dynamicRulesDto = new DynamicRulesDto();
        dynamicRulesDto.setId(dynamicRules.getId());
        dynamicRulesDto.setUserId(dynamicRules.getUserId());
        List<QueryDto> queryDtos = dynamicRules.getQueries().stream()
                        .map(this::toDto)
                                .collect(Collectors.toList());
        dynamicRulesDto.setQueries(queryDtos);
        dynamicRules.setProductName(dynamicRules.getProductName());
        dynamicRules.setProductId(dynamicRules.getProductId());
        dynamicRules.setProductText(dynamicRules.getProductText());

        return dynamicRulesDto;
    }

    private QueryDto toDto(Query query) {
        QueryDto queryDto = new QueryDto();
        queryDto.setQuery(query.getQuery());
        queryDto.setArguments(query.getArguments());
        queryDto.setNegate(query.isNegate());
        return queryDto;
    }

}