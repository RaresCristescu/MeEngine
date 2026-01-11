package com.app.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	            .components(new Components()
	                .addSecuritySchemes("JWTtoken",
	                    new SecurityScheme()
	                        .type(SecurityScheme.Type.APIKEY)
	                        .in(SecurityScheme.In.HEADER)
	                        .name("Authorization")
	                )
	            )
	            .addSecurityItem(
	                new SecurityRequirement().addList("JWTtoken")
	            );
	    }
}

