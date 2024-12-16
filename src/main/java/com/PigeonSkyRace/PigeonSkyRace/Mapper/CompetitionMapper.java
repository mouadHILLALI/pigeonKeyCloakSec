package com.PigeonSkyRace.PigeonSkyRace.Mapper;

import com.PigeonSkyRace.PigeonSkyRace.dto.Request.CreatingCompetitionRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.CompetitionCreatedResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    Competition RequestDtoToEntity(CreatingCompetitionRequestDto creatingCompetitionRequestDto);
    CompetitionCreatedResponseDto EntityToDto(Competition competition);
}
