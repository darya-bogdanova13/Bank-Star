package com.skypro.bank_star.service;

import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.repository.DynamicRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DynamicRulesService {

    private final DynamicRulesRepository dynamicRulesRepository;

    public DynamicRulesService(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    public List<DynamicRules> getRulesByUserId(Long userId) {
        return dynamicRulesRepository.findByUserId(userId);
    }
    public void addRule(DynamicRules rule) {
        dynamicRulesRepository.save(rule);
    }
    public void deleteRules(Long id){
        dynamicRulesRepository.deleteById(id);
    }
    public List<DynamicRules> getAllRules() {
        return dynamicRulesRepository.findAll();
    }

}