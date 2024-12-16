package com.PigeonSkyRace.PigeonSkyRace.service.User;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.UserMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.Request.ManageUserRoleRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Request.RegisterUserRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.ManagedUserRoleResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.RegisterUserResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.enums.UserRoles;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.UsernameAlreadyExistException;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;
import com.PigeonSkyRace.PigeonSkyRace.security.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Override
    public RegisterUserResponseDto Register(RegisterUserRequestDto user) {
        if (this.isUsernameExist(user.username())){
            throw new UsernameAlreadyExistException("Username :"+user.username()+"already exist");
        }
        User userToStore = User.builder().username(user.username()).password(passwordEncoder.encode(user.password())).role(UserRoles.ROLE_USER).build();
        userToStore = userRepository.save(userToStore);
        log.debug("user is:" + userToStore.getUsername());
        return userMapper.toRegisterUserResponseDto(userToStore);
    }

    @Override
    public ManagedUserRoleResponseDto manageUserRole(ManageUserRoleRequestDto user) {
        User userToManage = userRepository.findByUsername(user.username());
        switch (user.role()) {
            case "ROLE_USER":
                userToManage.setRole(UserRoles.ROLE_USER);
                break;
            case "ROLE_ADMIN":
                userToManage.setRole(UserRoles.ROLE_ADMIN);
                break;
            case "ROLE_ORGANIZER":
                userToManage.setRole(UserRoles.ROLE_ORGANIZER);
                break;
            default:
                userToManage.setRole(UserRoles.ROLE_USER);
                break;
        }
        userToManage = userRepository.save(userToManage);
        return userMapper.toManagedUserRoleResponseDto(userToManage);
    }

    @Override
    public boolean isUsernameExist(String username) {
        if (!userRepository.existsByUsername(username)){
            return false;
        }
        return true;
    }
}
