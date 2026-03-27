package com.example.demo.feaure;

import org.ff4j.FF4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableAutoConfiguration
class DemoFeatureFF4jTest {

    @Autowired
    private FF4j ff4j;

    @Autowired
    @Qualifier("mainDemoFeature")
    private DemoFeature demoFeature;

    @BeforeEach
    void resetFeatureState() {
        this.ff4j.enable("demoFeature");
    }

    @Test
    void whenFeatureIsEnabled_callsMainBeanLogic() {
        final String result = this.demoFeature.alternativeDemoFeature();

        assertEquals("AlternativeDemoFeature", result);
    }

    @Test
    void whenFeatureIsDisabled_callsAlternativeBeanLogic() {
        this.ff4j.disable("demoFeature");

        final String result = this.demoFeature.alternativeDemoFeature();

        assertEquals("MainDemoFeature", result);
    }
}


