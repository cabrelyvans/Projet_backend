package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entity.Alert;
import com.example.service.PilotageService;

@Configuration
public class DataTestRunner {

    @Bean
    CommandLineRunner testProfil(PilotageService profilService) {
        return args -> {
            Alert p = new Alert();
            p.setName("Test CommandLine");

            System.out.println("✅ Profil créé avec ID : " + p.getId());
        };
    }
}
