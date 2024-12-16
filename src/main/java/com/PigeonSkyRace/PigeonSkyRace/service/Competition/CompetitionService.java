package com.PigeonSkyRace.PigeonSkyRace.service.Competition;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.CreatingCompetitionRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.CompetitionCreatedResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;

public interface CompetitionService {
    CompetitionCreatedResponseDto createCompetition(CreatingCompetitionRequestDto requestDto);
}
