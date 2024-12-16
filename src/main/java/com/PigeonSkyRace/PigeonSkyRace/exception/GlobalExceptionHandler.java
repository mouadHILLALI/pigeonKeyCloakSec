package com.PigeonSkyRace.PigeonSkyRace.exception;

import com.PigeonSkyRace.PigeonSkyRace.dto.Response.ErrorDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalTime;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
    @ExceptionHandler(NoUserWasFoundException.class)
    public ResponseEntity<String> handleNoUserWasFoundException(NoUserWasFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(CompetitionNotFinishedException.class)
    public ResponseEntity<String> handleCompetitionNotFinishedException(CompetitionNotFinishedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " +ex.getMessage());
    }
    @ExceptionHandler(NoCompetitionWasFound.class)
    public ResponseEntity<String> handleCompetitionNotFoundException(NoCompetitionWasFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competition not found" + ex.getMessage());
    }
    @ExceptionHandler(NegativeDurationException.class)
    public ResponseEntity<String> handleNegativeDurationException(NegativeDurationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<ErrorDto> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getMessage(), LocalTime.now() , HttpStatus.BAD_REQUEST , request.getRequestURI()));
    }
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getMessage(), LocalTime.now(), HttpStatus.BAD_REQUEST , request.getRequestURI()));
    }
}
