package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final CustomAutheticationProvider customAutheticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return customAutheticationProvider.authenticate(authentication);
    }
}
