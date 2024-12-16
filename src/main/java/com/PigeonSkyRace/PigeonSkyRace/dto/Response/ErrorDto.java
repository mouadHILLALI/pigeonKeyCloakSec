package com.PigeonSkyRace.PigeonSkyRace.dto.Response;

import org.springframework.http.HttpStatus;

import java.time.LocalTime;

public record ErrorDto(String message , LocalTime timeStamp , HttpStatus status , String path) {
}
