package com.kata.boats.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // turn off CSRF for simplicity
                .csrf(AbstractHttpConfigurer::disable)

                // authorization rules
                .authorizeHttpRequests(auth -> auth
                        // allow anonymous GET on /api/boats (or GET /api/**)
                        .requestMatchers("/api/**").permitAll()
                        // everything else under /api/** still needs login:
                        .requestMatchers("/api/**").authenticated()
                        // all other URLs are public:
                        .anyRequest().permitAll()
                )

                // enable HTTP Basic & form login
                .httpBasic(withDefaults())
                .formLogin(withDefaults());

        return http.build();
    }
}