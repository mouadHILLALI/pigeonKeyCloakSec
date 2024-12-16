package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import com.PigeonSkyRace.PigeonSkyRace.dto.Response.AccessDeniedErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities()
                .stream().anyMatch(grantedAuthority ->
                        !grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(new AccessDeniedErrorDto(
                            "Admin access denied",
                            formattedTimestamp,
                            HttpStatus.FORBIDDEN,
                            request.getRequestURI()
                    ))
            );
        }else if(authentication != null && authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                !grantedAuthority.getAuthority().equals("ROLE_ORGANIZER"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(new AccessDeniedErrorDto("Organizer access denied" ,
                            formattedTimestamp ,
                            HttpStatus.FORBIDDEN,
                            request.getRequestURI()))
            );
        } else if (authentication == null && authentication.isAuthenticated() == false) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(new AccessDeniedErrorDto(
                            "Bad credentials" ,
                            formattedTimestamp ,
                            HttpStatus.BAD_REQUEST ,
                            request.getRequestURI()
                    ))
            );
        } else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(new AccessDeniedErrorDto(
                            "Access denied please authenticate first" ,
                            formattedTimestamp ,
                            HttpStatus.FORBIDDEN ,
                            request.getRequestURI()
                    ))
            );
        }
        }
    }
