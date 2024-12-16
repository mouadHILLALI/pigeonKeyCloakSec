package com.PigeonSkyRace.PigeonSkyRace.dto.Response;

import org.springframework.http.HttpStatus;

public record AccessDeniedErrorDto(String message , String timeStamp , HttpStatus status , String path) {
}
