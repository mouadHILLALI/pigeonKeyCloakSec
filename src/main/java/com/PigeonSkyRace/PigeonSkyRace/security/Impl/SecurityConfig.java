package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    
        @Bean
        public SecurityFilterChain configure(HttpSecurity http, CustomAuthenticationHandler successHandler) throws Exception {
            http.cors(cors -> cors.configurationSource(corsFilter()))
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/users/**").hasRole("USER")
                    .anyRequest().authenticated())
                .oauth2Login(login -> login
                    .loginPage("/oauth2/authorization/keycloak")
                    .successHandler(successHandler))
                .logout(logout -> logout
                    .logoutSuccessUrl("http://localhost:8082/realms/spring-boot-realm/oauth2/openid-connect/logout?post_logout_redirect_uri=https://localhost")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true));
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
}
