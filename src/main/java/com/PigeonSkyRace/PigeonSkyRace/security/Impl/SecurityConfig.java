package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    
        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
            http.cors(cors -> cors.configurationSource(corsFilter()))
            .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll() 
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/users/**").hasRole("USER")
                    .anyRequest().authenticated())
                .oauth2ResourceServer( (ouauth2) -> ouauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
            return http.build();
        }
        

   @Bean        
   public CorsConfigurationSource corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://localhost:8443"));
        configuration.setAllowedMethods(Arrays.asList("GET" , "POST" , "DELETE" , "PUT" , "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type" , "Accept" , "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
   } 

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var claims = jwt.getClaims();

            System.out.println("DEBUG: JWT Claims: " + claims);

            Object rolesClaim = claims.get("realm_access");
            if (rolesClaim != null) {
                Map<String, Object> realmAccess = (Map<String, Object>) rolesClaim;
                List<String> roles = (List<String>) realmAccess.get("roles");

                System.out.println("DEBUG: Extracted roles: " + roles);

                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))  
                        .collect(Collectors.toList());

                System.out.println("DEBUG: Extracted authorities:");
                authorities.forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));

                return authorities;
            }

            return Collections.emptyList();
        });

        return authenticationConverter;
    }

     @Bean
    public JwtDecoder jwtDecoder() {
        String keycloakUrl = "https://localhost:8444/realms/spring-boot-realm/protocol/openid-connect/certs";
        return NimbusJwtDecoder.withJwkSetUri(keycloakUrl).build();
    }

}
