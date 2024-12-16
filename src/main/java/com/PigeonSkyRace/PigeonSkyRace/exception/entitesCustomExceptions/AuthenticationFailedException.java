package com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
