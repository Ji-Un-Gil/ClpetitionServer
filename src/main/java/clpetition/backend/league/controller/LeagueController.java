package clpetition.backend.league.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.league.docs.AddLeagueApiDocs;
import clpetition.backend.league.docs.ChangeLeagueApiDocs;
import clpetition.backend.league.docs.GetLeagueRankMemberApiDocs;
import clpetition.backend.league.docs.GetLeagueRankTopFiftyApiDocs;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.league.service.LeagueService;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/league")
public class LeagueController implements
        AddLeagueApiDocs,
        ChangeLeagueApiDocs,
        GetLeagueRankTopFiftyApiDocs,
        GetLeagueRankMemberApiDocs {

    private final LeagueService leagueService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<Void>> addLeague(
            @FindMember Member member,
            @RequestParam(value = "difficulty") String difficulty
    ) {
        leagueService.addLeague(member, difficulty);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<Void>> changeLeague(
            @FindMember Member member,
            @RequestParam(value = "difficulty") String difficulty
    ) {
        leagueService.changeLeague(member, difficulty);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/top")
    public ResponseEntity<BaseResponse<List<GetLeagueRankResponse>>> getLeagueRankTopFifty(
            @FindMember Member member,
            @RequestParam(value = "difficulty") String difficulty
    ) {
        return BaseResponse.toResponseEntityContainsResult(leagueService.getLeagueRankTopFifty(difficulty));
    }

    @GetMapping("/member")
    public ResponseEntity<BaseResponse<GetLeagueRankMemberResponse>> getLeagueRankMember(
            @FindMember Member member,
            @RequestParam(value = "difficulty") String difficulty
    ) {
        return BaseResponse.toResponseEntityContainsResult(leagueService.getLeagueRankMember(member, difficulty));
    }
}
