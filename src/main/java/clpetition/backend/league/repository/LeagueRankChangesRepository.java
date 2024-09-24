package clpetition.backend.league.repository;

import clpetition.backend.league.domain.LeagueRankChanges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRankChangesRepository extends JpaRepository<LeagueRankChanges, Long> {
    Optional<LeagueRankChanges> findBySeason(Integer season);
}
