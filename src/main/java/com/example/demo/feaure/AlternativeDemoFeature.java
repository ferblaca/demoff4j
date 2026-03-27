package com.example.demo.feaure;

import org.springframework.stereotype.Component;

@Component("alternativeDemoFeature")
public class AlternativeDemoFeature implements DemoFeature {

    @Override
    public String alternativeDemoFeature() {
        return "AlternativeDemoFeature";
    }
}
