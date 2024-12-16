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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldThrowException_whenUsernameAlreadyExists() {
        // Given
        RegisterUserRequestDto registerUserRequestDto = new RegisterUserRequestDto("existingUsername", "password123");
        when(userRepository.existsByUsername(registerUserRequestDto.username())).thenReturn(true);

        // When & Then
        UsernameAlreadyExistException exception = assertThrows(UsernameAlreadyExistException.class, () -> {
            userService.Register(registerUserRequestDto);
        });

        assertEquals("Username :existingUsernamealready exist", exception.getMessage());
    }

    @Test
    void register_shouldReturnResponse_whenUsernameIsNew() {
        // Given
        RegisterUserRequestDto registerUserRequestDto = new RegisterUserRequestDto("newUsername", "password123");
        User newUser = User.builder().username("newUsername").password("password123").build();
        RegisterUserResponseDto responseDto = new RegisterUserResponseDto(UUID.randomUUID() ,"newUsername", "ROLE_USER" , "ROLE_USER");

        when(userRepository.existsByUsername(registerUserRequestDto.username())).thenReturn(false);
        when(passwordEncoder.encode(registerUserRequestDto.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(userMapper.toRegisterUserResponseDto(any(User.class))).thenReturn(responseDto);

        // When
        RegisterUserResponseDto result = userService.Register(registerUserRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("newUsername", result.username());
        assertEquals("ROLE_USER", result.role());
    }

    @Test
    void manageUserRole_shouldUpdateRole_whenValidRoleIsGiven() {
        // Given
        ManageUserRoleRequestDto manageUserRoleRequestDto = new ManageUserRoleRequestDto("existingUser", "ROLE_ADMIN");
        User newUser = User.builder().username("newUsername").password("password123").build();
        ManagedUserRoleResponseDto managedUserRoleResponseDto = new ManagedUserRoleResponseDto( UUID.randomUUID(), "existingUser", "ROLE_ADMIN");

        when(userRepository.findByUsername(manageUserRoleRequestDto.username())).thenReturn(newUser);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(userMapper.toManagedUserRoleResponseDto(any(User.class))).thenReturn(managedUserRoleResponseDto);

        // When
        ManagedUserRoleResponseDto result = userService.manageUserRole(manageUserRoleRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("existingUser", result.username());
        assertEquals("ROLE_ADMIN", result.role());
        verify(userRepository).save(newUser); // Ensure save was called
    }

    @Test
    void manageUserRole_shouldReturnDefaultRole_whenInvalidRoleIsGiven() {
        // Given
        ManageUserRoleRequestDto manageUserRoleRequestDto = new ManageUserRoleRequestDto("existingUser", "INVALID_ROLE");
        User newUser = User.builder().username("newUsername").password("password123").build();
        ManagedUserRoleResponseDto managedUserRoleResponseDto = new ManagedUserRoleResponseDto( UUID.randomUUID() , "existingUser", "ROLE_USER");

        when(userRepository.findByUsername(manageUserRoleRequestDto.username())).thenReturn(newUser);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(userMapper.toManagedUserRoleResponseDto(any(User.class))).thenReturn(managedUserRoleResponseDto);

        // When
        ManagedUserRoleResponseDto result = userService.manageUserRole(manageUserRoleRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("newUser", result.username());
        assertEquals("ROLE_USER", result.role());
    }

    @Test
    void isUsernameExist_shouldReturnTrue_whenUsernameExists() {
        // Given
        String username = "existingUser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // When
        boolean result = userService.isUsernameExist(username);

        // Then
        assertTrue(result);
    }

    @Test
    void isUsernameExist_shouldReturnFalse_whenUsernameDoesNotExist() {
        // Given
        String username = "newUser";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        // When
        boolean result = userService.isUsernameExist(username);

        // Then
        assertFalse(result);
    }
}