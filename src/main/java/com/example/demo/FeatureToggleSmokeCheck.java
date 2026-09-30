package com.example.demo;

import com.example.demo.feaure.DemoFeature;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.spring.autowire.FF4JFeature;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Verifies at startup that FF4J is still fully functional, so that the absence of the
 * {@code BeanPostProcessorChecker} WARNs cannot be mistaken for FF4J being silently disabled.
 *
 * <ul>
 *   <li>{@code @Flip} AOP routing must redirect to the alternative bean while the feature is ON.</li>
 *   <li>{@code @FF4JFeature} annotation-driven injection must still be performed by
 *       {@code AutowiredFF4JBeanPostProcessor}.</li>
 * </ul>
 */
@Component
public class FeatureToggleSmokeCheck implements CommandLineRunner {

	@FF4JFeature("demoFeature")
	private Feature injectedFeature;

	@FF4JFeature("demoFeature")
	private boolean injectedFeatureEnabled;

	@FF4JFeature("customizedFeature")
	private boolean customizedFeatureEnabled;

	private final DemoFeature demoFeature;

	private final FF4j ff4j;

	public FeatureToggleSmokeCheck(final DemoFeature demoFeature, final FF4j ff4j) {
		this.demoFeature = demoFeature;
		this.ff4j = ff4j;
	}

	@Override
	public void run(final String... args) {
		check("ff4j bean available", ff4j != null);
		check("feature 'demoFeature' exists and is ON", ff4j.check("demoFeature"));

		// @Flip: feature is ON, so the call must be routed to 'alternativeDemoFeature'
		final String result = demoFeature.alternativeDemoFeature();
		check("@Flip routes to AlternativeDemoFeature (got: " + result + ")",
				"AlternativeDemoFeature".equals(result));

		// @FF4JFeature: injection performed by AutowiredFF4JBeanPostProcessor
		check("@FF4JFeature Feature injected", injectedFeature != null && injectedFeature.isEnable());
		check("@FF4JFeature boolean injected", injectedFeatureEnabled);
		check("@FF4JFeature injected from a feature created by a collaborator bean", customizedFeatureEnabled);

		System.out.println("FF4J SMOKE CHECK: ALL OK");
	}

	private void check(final String what, final boolean condition) {
		if (!condition) {
			throw new IllegalStateException("FF4J SMOKE CHECK FAILED: " + what);
		}
		System.out.println("FF4J SMOKE CHECK OK: " + what);
	}
}
