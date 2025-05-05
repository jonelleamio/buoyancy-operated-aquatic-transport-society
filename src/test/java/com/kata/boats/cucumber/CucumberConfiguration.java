package com.kata.boats.cucumber;

import com.kata.boats.BuoyancyOperatedAquaticTransportSocietyApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest(
        classes = {BuoyancyOperatedAquaticTransportSocietyApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public record CucumberConfiguration (){
}