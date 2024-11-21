package com.skypro.bank_star.controller;

import com.skypro.bank_star.model.DynamicRules;
import com.skypro.bank_star.service.DynamicRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rules")
public class DynamicRulesController {
    private final DynamicRulesService dynamicRulesService;

    @Autowired
    public DynamicRulesController(DynamicRulesService dynamicRulesService) {
        this.dynamicRulesService = dynamicRulesService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DynamicRules>> getRulesByUserId(@PathVariable Long userId) {
        List<DynamicRules> rules = dynamicRulesService.getRulesByUserId(userId);
        return ResponseEntity.ok(rules);
    }

    @PostMapping
    public ResponseEntity<Void> addRule(@RequestBody DynamicRules rule) {
        dynamicRulesService.addRule(rule);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        dynamicRulesService.deleteRules(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DynamicRules>> getAllRules() {
        List<DynamicRules> rules = dynamicRulesService.getAllRules();
        return ResponseEntity.ok(rules);
    }

}