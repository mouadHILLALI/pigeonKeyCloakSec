package com.PigeonSkyRace.PigeonSkyRace.dto.Response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CompetitionCreatedResponseDto(UUID id , String name , String description , LocalDate creationTime) {
}
