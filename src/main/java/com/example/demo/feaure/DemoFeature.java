package com.example.demo.feaure;

import org.ff4j.aop.Flip;

public interface DemoFeature {

    @Flip(name = "demoFeature", alterBean = "alternativeDemoFeature")
    String alternativeDemoFeature();

}
