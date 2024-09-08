package clpetition.backend.league.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.league.domain.League;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.league.dto.response.GetMainLeagueResponse;
import clpetition.backend.league.repository.LeagueRepository;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static clpetition.backend.league.domain.League.SEASON;

@Service
@RequiredArgsConstructor
@Transactional
public class LeagueService {

    private final LeagueRepository leagueRepository;

    /**
     * 리그 추가
     * */
    public void addLeague(Member member, String difficulty) {
        isAlreadyExist(member);
        toLeague(member, difficulty);
    }

    /**
     * 리그 수정(삭제 후 저장)
     * */
    public void changeLeague(Member member, String difficulty) {
        deleteLeague(member);
        toLeague(member, difficulty);
    }

    /**
     * 리그 TOP 50 조회
     * */
    public List<GetLeagueRankResponse> getLeagueRankTopFifty(String difficulty) {
        return toGetLeagueRankTopFiftyResponseList(Difficulty.findByKey(difficulty));
    }

    /**
     * 리그 사용자 순위 조회
     * */
    public GetLeagueRankMemberResponse getLeagueRankMember(Member member, String difficulty) {
        isExist(member, Difficulty.findByKey(difficulty));
        return toGetLeagueRankMemberResponse(member, Difficulty.findByKey(difficulty));
    }

    /**
     * 리그 사용자 간략한 정보 조회 (선택 난이도, 순위)
     * */
    public Map<String, Object> getLeagueBrief(Member member) {
        Optional<League> league = getLeague(member, SEASON);

        if (league.isEmpty())
            return Map.of("difficulty", Optional.empty(), "rank", Optional.empty());

        Difficulty difficulty = league.get().getDifficulty();
        String rank = getLeagueRank(member, SEASON, difficulty);

        return Map.of("difficulty", difficulty.getKey(), "rank", rank);
    }

    /**
     * 홈 이달의리그 정보 조회
     * */
    public GetMainLeagueResponse getMainLeague(Member member) {
        Optional<League> league = getLeague(member, SEASON);
        Optional<League> recentLeague = getLeague(member, SEASON - 1);

        if (league.isEmpty())
            return GetMainLeagueResponse.builder().build();

        Difficulty difficulty = league.get().getDifficulty();
        Map<String, Object> rankAndTotalSend = getLeagueRankAndTotalSend(member, difficulty);

        if (recentLeague.isEmpty())
            return toGetMainLeagueResponse(difficulty, rankAndTotalSend, null);
        String recentRank = getLeagueRank(member, SEASON - 1, difficulty);

        int recentRankInt = Integer.parseInt(recentRank);
        int currentRankInt = Integer.parseInt(rankAndTotalSend.get("rank").toString());
        int rankDifference = recentRankInt - currentRankInt;

        return toGetMainLeagueResponse(difficulty, rankAndTotalSend, rankDifference);
    }

    /**
     * 리그 삭제 (hard delete)
     * */
    private void deleteLeague(Member member) {
        leagueRepository.deleteByMemberAndSeason(member, SEASON);
    }

    /**
     * 리그 추가
     * */
    private void toLeague(Member member, String difficulty) {
        League league = leagueRepository.save(
                League.builder()
                        .difficulty(Difficulty.findByKey(difficulty))
                        .member(member)
                        .build()
        );
    }

    /**
     * 이미 리그에 추가되어 있는 상태에서는 추가 금지
     * */
    private void isAlreadyExist(Member member) {
        if (leagueRepository.existsByMemberAndSeason(member, SEASON))
            throw new BaseException(BaseResponseStatus.LEAGUE_ALREADY_REGISTERED_ERROR);
    }

    /**
     * 탐색할 리그에 사용자가 존재하지 않을 시 예외
     * */
    private void isExist(Member member, Difficulty difficulty) {
        if (!leagueRepository.existsByMemberAndSeasonAndDifficulty(member, SEASON, difficulty))
            throw new BaseException(BaseResponseStatus.LEAGUE_MEMBER_NOT_FOUND_ERROR);
    }

    /**
     * 리그 TOP 50 조회 to dto
     * */
    private List<GetLeagueRankResponse> toGetLeagueRankTopFiftyResponseList(Difficulty difficulty) {
        return leagueRepository.getLeagueRankTopFifty(SEASON, difficulty);
    }

    /**
     * 리그 사용자 순위 조회 to dto
     * */
    private GetLeagueRankMemberResponse toGetLeagueRankMemberResponse(Member member, Difficulty difficulty) {
        return leagueRepository.getLeagueRankMember(member, SEASON, difficulty);
    }

    /**
     * 사용자가 등록된 리그 조회 (난이도)
     * */
    private Optional<League> getLeague(Member member, Integer season) {
        return leagueRepository.findByMemberAndSeason(member, season);
    }

    /**
     * 리그 사용자 간략한 정보 조회 (순위)
     * */
    private String getLeagueRank(Member member, Integer season, Difficulty difficulty) {
        return leagueRepository.getLeagueRank(member, season, difficulty);
    }

    /**
     * 홈 이달의리그 정보 조회 (순위, 완등 수)
     * */
    private Map<String, Object> getLeagueRankAndTotalSend(Member member, Difficulty difficulty) {
        return leagueRepository.getLeagueRankAndTotalSend(member, SEASON, difficulty);
    }

    /**
     * 홈 이달의리그 정보 조회 to dto
     * */
    private GetMainLeagueResponse toGetMainLeagueResponse(Difficulty difficulty, Map<String, Object> rankAndTotalSend, Integer gapRecentMonth) {
        return GetMainLeagueResponse.builder()
                .difficulty(difficulty.getKey())
                .rank(rankAndTotalSend.get("rank").toString())
                .totalSend((Integer) rankAndTotalSend.get("totalSend"))
                .gapRecentMonth(gapRecentMonth)
                .build();
    }
}
