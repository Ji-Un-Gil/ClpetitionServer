package clpetition.backend.league.repository;

import clpetition.backend.league.domain.League;
import clpetition.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long>, LeagueQueryRepository {
    void deleteByMemberAndSeason(Member member, Integer season);
    boolean existsByMemberAndSeason(Member member, Integer season);
}
