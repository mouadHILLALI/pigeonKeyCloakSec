package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.ManageUserRoleRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.ManagedUserRoleResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("manage")
    public ResponseEntity<ManagedUserRoleResponseDto> manageUserRole(@RequestBody @Valid ManageUserRoleRequestDto user) {
        return ResponseEntity.ok(userService.manageUserRole(user));
    }
}
