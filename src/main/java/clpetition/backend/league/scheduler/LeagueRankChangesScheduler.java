package clpetition.backend.league.scheduler;

import clpetition.backend.league.domain.LeagueRankChanges;
import clpetition.backend.league.repository.LeagueRankChangesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LeagueRankChangesScheduler {

    private final LeagueRankChangesRepository leagueRankChangesRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void resetLeagueRankChanges() {
        int currentSeason = (LocalDate.now().getYear() - 2024) * 12 + (LocalDate.now().getMonthValue() - 9) + 1;

        LeagueRankChanges leagueRankChanges = LeagueRankChanges.builder()
                .season(currentSeason)
                .changes(0)
                .build();

        leagueRankChangesRepository.save(leagueRankChanges);
    }
}
