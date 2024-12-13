package com.skypro.bank_star.service.impl;

import com.skypro.bank_star.dto.DynamicRulesDto;
import com.skypro.bank_star.dto.RecommendationsDto;
import com.skypro.bank_star.exception.RuleNotFoundException;
import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.model.Query;
import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.repository.DynamicJPARecommendationsRepository;
import com.skypro.bank_star.request.arguments.ComparisonOperators;
import com.skypro.bank_star.request.arguments.SumCompare;
import com.skypro.bank_star.request.arguments.TransactionProductTypes;
import com.skypro.bank_star.service.DynamicRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DynamicRulesServiceImpl implements DynamicRulesService {
    private final Logger logger = LoggerFactory.getLogger(DynamicRulesServiceImpl.class);
    private final DynamicJPARecommendationsRepository dynamicJPARecommendationsRepository;

    @Autowired
    public DynamicRulesServiceImpl(DynamicJPARecommendationsRepository dynamicJPARecommendationsRepository) {
        this.dynamicJPARecommendationsRepository = dynamicJPARecommendationsRepository;
    }

    @Override
    public Recommendations createDynamicRuleRecommendation(RecommendationsDto recommendationsDto) {

        logger.info("Starting adding recommendation in database for recommendations: {}", recommendationsDto);
        Recommendations recommendation = new Recommendations();
        recommendation.setProductName(recommendationsDto.getProductName());
        recommendation.setProductId(recommendationsDto.getProductId());
        recommendation.setProductText(recommendationsDto.getProductText());
        logger.info("Successfully added recommendation in database for recommendation: {}", recommendation);

        Query query = new Query(0);
        query.setRecommendations(recommendation);
        recommendation.setQuery(query);
        logger.info("Successfully added value {} in dynamic recommendations stats count", query.getCount());

        logger.info("Starting adding rules in database for recommendations: {}", recommendationsDto);
        List<DynamicRules> rules = recommendationsDto.getRule().stream()
                .map(dynamicRulesDto -> {
                    logger.info("Start checking query and arguments for adding rule: {}", dynamicRulesDto);
                    checkArguments(dynamicRulesDto.getArguments());
                    logger.info("End checking query and arguments for adding rule: {}", dynamicRulesDto);

                    DynamicRules dynamicRules = new DynamicRules();
                    dynamicRules.setQuery(dynamicRulesDto.getQuery());
                    dynamicRules.setArguments(dynamicRulesDto.getArguments()
                            .stream()
                            .map(String::toUpperCase)
                            .collect(Collectors.toList()));
                    dynamicRules.setNegate(dynamicRulesDto.isNegate());
                    dynamicRules.setRecommendations(recommendation);
                    return dynamicRules;})
                .collect(Collectors.toList());
        recommendation.setRule(rules);
        logger.info("Successfully added rules in database for recommendation");
        return dynamicJPARecommendationsRepository.save(recommendation);
    }

    @Override
    public Optional<RecommendationsDto> getDynamicRuleRecommendation(UUID recommendationId) throws RuleNotFoundException {

        logger.info("Starting checking dynamic rule in database for id: {}", recommendationId);
        if (dynamicJPARecommendationsRepository.existsById(recommendationId)) {

            logger.info("Starting getting recommendation from database for id: {}", recommendationId);
            return dynamicJPARecommendationsRepository.findById(recommendationId)
                    .map(recommendations -> {
                        RecommendationsDto recommendationsDto= new RecommendationsDto();
                        recommendationsDto.setId(recommendations.getId());
                        recommendationsDto.setProductName(recommendations.getProductName());
                        recommendationsDto.setProductId(recommendations.getProductId());
                        recommendationsDto.setProductText(recommendations.getProductText());

                        List<DynamicRulesDto> dynamicRulesDto = recommendations.getRule()
                                .stream()
                                .map(rule -> {
                                    DynamicRulesDto dynamicRuleDto = new DynamicRulesDto();
                                    dynamicRuleDto.setId(dynamicRuleDto.getId());
                                    dynamicRuleDto.setQuery(dynamicRuleDto.getQuery());
                                    dynamicRuleDto.setArguments(dynamicRuleDto.getArguments());
                                    dynamicRuleDto.setNegate(dynamicRuleDto.isNegate());
                                        return dynamicRuleDto;})
                                .collect(Collectors.toList());

                        recommendationsDto.setRule(dynamicRulesDto);
                            logger.info("Successfully got rules from database for for id: {}", recommendationId);
                            return recommendationsDto; });
        }
        logger.error("Error checking dynamic rule in database for id: {}", recommendationId);
        throw new RuleNotFoundException("Dynamic rule not found in database");
    }

    @Override
    public List<RecommendationsDto> getAllDynamicRulesRecommendations() {

        logger.info("Starting getting all recommendations from database");
        return dynamicJPARecommendationsRepository.findAll()
                .stream()
                .map(recommendation -> {
                    RecommendationsDto recommendationsDto = new RecommendationsDto();
                    recommendationsDto.setId(recommendation.getId());
                    recommendationsDto.setProductName(recommendation.getProductName());
                    recommendationsDto.setProductId(recommendation.getProductId());
                    recommendationsDto.setProductText(recommendation.getProductText());

                    List<DynamicRulesDto> rulesDtos = recommendation.getRule()
                            .stream()
                            .map(rule -> {
                                DynamicRulesDto dynamicRuleDto = new DynamicRulesDto();
                                dynamicRuleDto.setId(rule.getId());
                                dynamicRuleDto.setQuery(rule.getQuery());
                                dynamicRuleDto.setArguments(rule.getArguments());
                                dynamicRuleDto.setNegate(rule.isNegate());
                                return dynamicRuleDto; })
                            .collect(Collectors.toList());

                    recommendationsDto.setRule(rulesDtos);
                    logger.info("Successfully got all recommendations from database");
                    return recommendationsDto;})
                .collect(Collectors.toList());}

    @Override
    public void deleteDynamicRuleRecommendation(UUID recommendationId) throws RuleNotFoundException {

        logger.info("Starting checking dynamic rule for deleting in database for id: {}", recommendationId);
        if (dynamicJPARecommendationsRepository.existsById(recommendationId)) {

            dynamicJPARecommendationsRepository.deleteById(recommendationId);
            logger.info("Rule with id: {} was successfully deleted from database", recommendationId);
            } else {
            logger.error("Error checking dynamic rule in database for deleting for id: {}", recommendationId);
            throw new RuleNotFoundException("Dynamic rule not found in database");
        }
    }

    public void checkArguments(List<String> arguments) throws IllegalArgumentException {
        for (String argument : arguments) {
            if (!isValidEnumArguments(argument)) {
                logger.error("Error argument must be valid ENUMs value");
                throw new IllegalArgumentException("Argument \"" + argument + "\" should be one of the class's values in rulesArgumentsENUM");
            }
        }
    }

    public boolean isValidEnumArguments(String arguments) {
        return isTransactionProductType(arguments)
                || isComparisonOperators(arguments)
                || isSumCompare(arguments);
    }

    public boolean isTransactionProductType(String arguments) throws IllegalArgumentException {
        try {

            TransactionProductTypes.valueOf(arguments.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isComparisonOperators(String arguments) {
        return Arrays.stream(ComparisonOperators.values())
                .map(ComparisonOperators::getOperatorVal)
                .anyMatch(operator -> operator.equals(arguments));
    }

    public boolean isSumCompare(String arguments) {
        return Arrays.stream(SumCompare.values())
                .map(sum -> String.valueOf(sum.getSumVal()))
                .anyMatch(sumVal -> sumVal.equals(arguments));
        }

    }