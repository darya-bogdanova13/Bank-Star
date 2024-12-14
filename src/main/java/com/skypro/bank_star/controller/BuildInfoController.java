package com.skypro.bank_star.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/management")
public class BuildInfoController {

    private final Logger logger = LoggerFactory.getLogger(BuildInfoController.class);

    private final BuildProperties buildProperties;

    public BuildInfoController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping(path = "/info")
    @Operation(
            summary = "Возврат информации о сервисе",
            description = "Позволяет осуществить возврат информации о сервисе")
    public ResponseEntity<BuildProperties> getBuildInfo() {

        logger.info("Received request for projects build info");
        return ResponseEntity.ok(buildProperties);
    }

}