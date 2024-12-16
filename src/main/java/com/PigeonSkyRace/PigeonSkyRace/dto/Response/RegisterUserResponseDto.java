package com.PigeonSkyRace.PigeonSkyRace.dto.Response;

import java.util.UUID;

public record RegisterUserResponseDto(UUID id , String username , String password , String role) {
}
