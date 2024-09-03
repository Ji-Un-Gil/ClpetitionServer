package clpetition.backend.league.repository;

import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.league.domain.League;
import clpetition.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long>, LeagueQueryRepository {
    void deleteByMemberAndSeason(Member member, Integer season);
    boolean existsByMemberAndSeason(Member member, Integer season);
    boolean existsByMemberAndSeasonAndDifficulty(Member member, Integer season, Difficulty difficulty);
    Optional<League> findByMemberAndSeason(Member member, Integer season);
}
