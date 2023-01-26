package com.formation.exercice4.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class DocumnetationConfiguration {
    
    @Bean
    public OpenAPI apiDoc() {
        return new OpenAPI()
            .info(new Info().title("api accenture todomanager")
                            .description("api rest/json de gestion des todliste")
                            .version("0.8")
                            .license(new License().identifier("Accenture open")));
    }
}
