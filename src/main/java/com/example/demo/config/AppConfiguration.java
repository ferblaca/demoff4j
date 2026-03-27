package com.example.demo.config;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({"org.ff4j.aop", "com.example.demo"})
public class AppConfiguration {

	@Bean
	FF4j ff4j() {
		final FF4j ff4j = new FF4j();
		ff4j.createFeature(new Feature("demoFeature", true));
		return ff4j;
	}
}

