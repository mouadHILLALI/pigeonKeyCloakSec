package com.PigeonSkyRace.PigeonSkyRace.dto.Request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record ManageUserRoleRequestDto(@NotBlank(message = "username cannot be blank") String username , @NotBlank String role){
}
