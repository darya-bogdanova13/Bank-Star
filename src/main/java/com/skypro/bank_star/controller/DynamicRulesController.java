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

    @Autowired
    private DynamicRulesService dynamicRulesService;

    public DynamicRulesController(DynamicRulesService dynamicRulesService) {
        this.dynamicRulesService = dynamicRulesService;
    }

    @PostMapping
    public ResponseEntity<DynamicRules> addRule(@RequestBody DynamicRules rule) {
        DynamicRules createdRules = dynamicRulesService.addRule(rule);
        return ResponseEntity.ok(createdRules);
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

