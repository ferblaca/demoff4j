package com.example.demo.feaure;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("mainDemoFeature")
public class MainDemoFeature implements DemoFeature {

    @Override
    public String alternativeDemoFeature() {
        return "MainDemoFeature";
    }
}
