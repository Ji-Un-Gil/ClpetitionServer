package clpetition.backend.league.service;

import clpetition.backend.league.domain.LeagueRankChanges;
import clpetition.backend.league.repository.LeagueRankChangesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static clpetition.backend.league.domain.League.SEASON;

@Service
@RequiredArgsConstructor
@Transactional
public class LeagueRankChangesService {

    private final LeagueRankChangesRepository leagueRankChangesRepository;

    /**
     * 리그 순위 변동 횟수 수정
     * */
    public void plusLeagueRankChanges() {
        Optional<LeagueRankChanges> leagueRankChanges = findLeagueRankChanges();
        if (leagueRankChanges.isEmpty())
            return;
        increaseLeagueRankChanges(leagueRankChanges.get());
    }

    /**
     * 리그 순위 변동 횟수 조회
     * */
    public Integer getLeagueRankChanges() {
        Optional<LeagueRankChanges> leagueRankChanges = findLeagueRankChanges();
        if (leagueRankChanges.isEmpty())
            return 0;
        return leagueRankChanges.get().getChanges();
    }

    /**
     * 리그 순위 변동 횟수 +1
     * */
    private void increaseLeagueRankChanges(LeagueRankChanges leagueRankChanges) {
        leagueRankChangesRepository.save(
                LeagueRankChanges.builder()
                        .id(leagueRankChanges.getId())
                        .season(leagueRankChanges.getSeason())
                        .changes(leagueRankChanges.getChanges() + 1)
                        .build()
        );
    }

    /**
     * 리그 순위 변동 조회
     * */
    private Optional<LeagueRankChanges> findLeagueRankChanges() {
        return leagueRankChangesRepository.findBySeason(SEASON);
    }
}
