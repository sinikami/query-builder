package com.monad.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { ExampleApplication.AppBasePackages })
public class ExampleApplication {
    public static final String AppBasePackages = "com.monad.example";

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
