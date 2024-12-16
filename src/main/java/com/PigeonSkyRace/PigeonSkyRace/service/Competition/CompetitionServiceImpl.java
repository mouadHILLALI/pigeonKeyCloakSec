package com.PigeonSkyRace.PigeonSkyRace.service.Competition;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.CompetitionMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.Request.CreatingCompetitionRequestDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.Response.CompetitionCreatedResponseDto;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.repository.CompetitionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;
    @Override
    public CompetitionCreatedResponseDto createCompetition(CreatingCompetitionRequestDto requestDto) {
        Competition competition = Competition.builder().name(requestDto.name())
                .description(requestDto.description())
                .creationDate(LocalDate.now())
                .build();
        competition = competitionRepository.save(competition);
        return competitionMapper.EntityToDto(competition);
    }
}
