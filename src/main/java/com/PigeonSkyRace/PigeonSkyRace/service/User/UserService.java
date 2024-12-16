package com.PigeonSkyRace.PigeonSkyRace.service.User;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.ManageUserRoleRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Request.RegisterUserRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.ManagedUserRoleResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.RegisterUserResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    RegisterUserResponseDto Register(RegisterUserRequestDto user);
    ManagedUserRoleResponseDto manageUserRole(ManageUserRoleRequestDto user);
    boolean isUsernameExist(String username);
}
