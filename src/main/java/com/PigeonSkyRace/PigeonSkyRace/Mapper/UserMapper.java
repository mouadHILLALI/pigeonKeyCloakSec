package com.PigeonSkyRace.PigeonSkyRace.Mapper;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.RegisterUserRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.ManagedUserRoleResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.RegisterUserResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterUserResponseDto toRegisterUserResponseDto(User user);
    User toUser(RegisterUserRequestDto registerUserRequestDto);
    ManagedUserRoleResponseDto toManagedUserRoleResponseDto(User user);
}
