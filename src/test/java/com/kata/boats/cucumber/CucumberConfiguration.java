package com.kata.boats.cucumber;

import com.kata.boats.BuoyancyOperatedAquaticTransportSocietyApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest(
        classes = {BuoyancyOperatedAquaticTransportSocietyApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
public record CucumberConfiguration() {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17.4-alpine")
                    .withDatabaseName("boats")
                    .withUsername("root")
                    .withPassword("root")
                    .withStartupTimeout(Duration.ofSeconds(30))
                    .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void overrideDataSourceProps(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }
}