package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.RegisterUserRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.RegisterUserResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<RegisterUserResponseDto> register(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
            return ResponseEntity.ok(userService.Register(registerUserRequestDto));
    }
}
