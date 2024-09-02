package clpetition.backend.league.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.league.domain.League;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.league.repository.LeagueRepository;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return toGetLeagueRankMemberResponse(member, Difficulty.findByKey(difficulty));
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
        if(leagueRepository.existsByMemberAndSeason(member, SEASON))
            throw new BaseException(BaseResponseStatus.LEAGUE_ALREADY_REGISTERED_ERROR);
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
}
