package com.skypro.bank_star.service;

import com.skypro.bank_star.model.DynamicRules;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DynamicRulesService {

    private final List<DynamicRules> rules = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public DynamicRules addRule(DynamicRules rule) {
        long id = idCounter.incrementAndGet();
        rule.setId(id);
        rules.add(rule);
        return rule;
    }

    public void deleteRules(Long id) {
        rules.removeIf(rule -> rule.getId().equals(id));
    }

    public List<DynamicRules> getAllRules() {
        return new ArrayList<>(rules);
    }

    public Optional<DynamicRules> getRuleById(Long id) {
        return rules.stream().filter(rule -> rule.getId().equals(id)).findFirst();
    }
}