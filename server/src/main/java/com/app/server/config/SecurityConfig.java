package com.app.server.config;


import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.server.filters.JWTFilter;

@Configuration
public class SecurityConfig {
	private final JWTFilter jWTTokenValidatorFilter;

	public SecurityConfig(JWTFilter jWTTokenValidatorFilter) {
		this.jWTTokenValidatorFilter = jWTTokenValidatorFilter;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrfConfig -> csrfConfig.disable())
			.sessionManagement(
					sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())// requiresSecure
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests((requests) -> requests
					.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
					.requestMatchers("/").permitAll()
					.requestMatchers("/api/user/register", "/api/auth/login").permitAll()
					 .requestMatchers(
			                    "/", 
			                    "/index.html",
			                    "/favicon.ico",
			                    "/assets/**",        // JS, CSS, images
			                    "/static/**"         // sometimes Vite outputs here
			                ).permitAll()
					.requestMatchers("/api/**").authenticated())
			.addFilterBefore(jWTTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
