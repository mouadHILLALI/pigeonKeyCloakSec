package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.AuthenticationFailedException;
import com.PigeonSkyRace.PigeonSkyRace.security.CustomUserDetailsService;
import com.PigeonSkyRace.PigeonSkyRace.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAutheticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    @Qualifier("customUserDetailsService")
    private final CustomUserDetailsService userDetailsService;
    @Value("${spring.security.test.password-bypass:false}")
    private boolean password_bypass;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (password_bypass){
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationFailedException("Bad credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
