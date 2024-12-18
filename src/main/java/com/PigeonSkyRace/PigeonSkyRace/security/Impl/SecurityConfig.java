package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAutheticationProvider customAutheticationProvider;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
           return http.csrf(customizer -> customizer.disable())
                   .cors(customizer -> customizer.disable())
           .authorizeRequests(customizer -> customizer.requestMatchers("/api/public/**").permitAll()
                   .requestMatchers("/api/users/**").hasAnyAuthority("ROLE_USER")
                   .requestMatchers("/api/organizers/**").hasAnyAuthority("ROLE_ORGANIZER")
                   .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN").anyRequest().authenticated())
                   .authenticationManager(customAuthenticationManager)
                   .authenticationProvider(customAutheticationProvider)
                   .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler))
                   .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customAuthenticationEntryPoint))
        .build();
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
