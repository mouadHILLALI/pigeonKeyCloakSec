package com.PigeonSkyRace.PigeonSkyRace.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record RegisterUserRequestDto(@NotEmpty(message = "Username cannot be empty") @NotBlank String username , @NotEmpty(message = "Password cannot be empty") String password ) {
}
