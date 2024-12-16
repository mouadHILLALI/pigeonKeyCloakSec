package com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
