package com.skypro.bank_star.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Recommendation Service API",
                description = "Recommendation Service", version = "0.1.0"
        ),
        servers = @Server(
                url = "http://localhost:8080/",
                description = "Developer server"
        )
)
public class ApiConfig {

}