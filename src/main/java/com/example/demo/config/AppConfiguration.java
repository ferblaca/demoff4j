package com.example.demo.config;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mirrors the way a Spring Boot application typically wires FF4J: the FF4J AOP/Spring packages are component-scanned
 * and the {@code FF4j} bean is built from other beans of the context.
 *
 * <p>This is enough to reproduce the {@code BeanPostProcessorChecker} startup WARNs: {@code org.ff4j.spring} contains
 * {@code AutowiredFF4JBeanPostProcessor}, a {@link org.springframework.beans.factory.config.BeanPostProcessor} that
 * injects {@code FF4j} eagerly, forcing the whole {@code ff4j} dependency graph to be created too early.</p>
 */
@Configuration
@ComponentScan({"org.ff4j.aop", "org.ff4j.spring", "com.example.demo"})
public class AppConfiguration {

	@Bean
	FF4j ff4j(final FeatureStoreCustomizer customizer, final AuditListener auditListener) {
		final FF4j ff4j = new FF4j();
		ff4j.createFeature(new Feature("demoFeature", true));
		customizer.customize(ff4j);
		auditListener.register(ff4j);
		return ff4j;
	}

	@Bean
	FeatureStoreCustomizer featureStoreCustomizer() {
		return new FeatureStoreCustomizer();
	}

	@Bean
	AuditListener auditListener() {
		return new AuditListener();
	}

	/**
	 * Collaborator of the {@code ff4j} bean, used to show that its dependencies are dragged into the early
	 * instantiation as well.
	 */
	public static class FeatureStoreCustomizer {

		public void customize(final FF4j ff4j) {
			ff4j.createFeature(new Feature("customizedFeature", true));
		}
	}

	/**
	 * Second collaborator of the {@code ff4j} bean.
	 */
	public static class AuditListener {

		public void register(final FF4j ff4j) {
			ff4j.setEnableAudit(false);
		}
	}
}
