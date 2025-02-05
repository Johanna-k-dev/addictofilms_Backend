package com.visiotech.addictofilms.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Origines autorisées
    //     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Méthodes autorisées
    //     configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Headers autorisés
    //     configuration.setAllowCredentials(true); // Permet l'authentification avec des cookies/token
    //
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration); // Appliquer à toutes les routes
    //     return source;
    // }

}
