package com.example.demo_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {
        "com.example.demo_batch", // package racine
        "config"                  // pour BatchConfig, CsvReaderConfig
})
@EntityScan(basePackages = {
        "model"                   // les entités (Person)
})
public class DemoBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoBatchApplication.class, args);
    }
}
