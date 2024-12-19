package com.PigeonSkyRace.PigeonSkyRace.security.Impl;


import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  throws IOException, ServletException{
        String role = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
        .findFirst().orElse("");

        if (role.equals("ADMIN")) {
            setDefaultTargetUrl("/api/admin/manage");
        }else{
            setDefaultTargetUrl("/api/users/message");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}