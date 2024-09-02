package clpetition.backend.league.repository;

import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.member.domain.Member;

import java.util.List;

public interface LeagueQueryRepository {
    List<GetLeagueRankResponse> getLeagueRankTopFifty(Integer season, Difficulty difficulty);
    GetLeagueRankMemberResponse getLeagueRankMember(Member member, Integer season, Difficulty difficulty);
}
