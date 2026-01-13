package com.app.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.app.data.entity") // TODO look for better options
@EnableJpaRepositories("com.app.data.repo") // TODO look for better options
@ComponentScan(basePackages = "com.app")
public class DataConfig {

}
