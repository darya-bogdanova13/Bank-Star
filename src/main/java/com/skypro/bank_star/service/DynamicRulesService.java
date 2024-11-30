package com.skypro.bank_star.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.repository.DynamicRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DynamicRulesService {

    private final DynamicRulesRepository dynamicRulesRepository;

    @Autowired
    public DynamicRulesService(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    public List<DynamicRules> getRulesByUserId(Long userId) {
        return dynamicRulesRepository.findByUserId(userId);
    }
    public void addRule(DynamicRules rule) throws JsonProcessingException {
        dynamicRulesRepository.addRule(rule);
    }
    public void deleteRules(Long id){
        dynamicRulesRepository.deleteById(id);
    }
    public List<DynamicRules> getAllRules() {
        return dynamicRulesRepository.findAll();
    }

}