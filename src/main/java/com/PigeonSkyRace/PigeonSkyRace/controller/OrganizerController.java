package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.CreatingCompetitionRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.CompetitionCreatedResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.service.Competition.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizers/")
@RequiredArgsConstructor
public class OrganizerController {
    private final CompetitionService competitionService;

    @PostMapping("createCompetition")
    public ResponseEntity<CompetitionCreatedResponseDto> create(@RequestBody CreatingCompetitionRequestDto requestDto) {
        return ResponseEntity.ok(competitionService.createCompetition(requestDto));
    }
}
