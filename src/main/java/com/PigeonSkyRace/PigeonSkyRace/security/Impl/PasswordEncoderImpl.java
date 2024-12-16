package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import com.PigeonSkyRace.PigeonSkyRace.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoder {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encoder.matches(rawPassword, encodedPassword)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
