package clpetition.backend.league.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.league.docs.*;
import clpetition.backend.league.dto.response.GetLeagueBannerResponse;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.league.dto.response.GetRankResponse;
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
        GetLeagueRankMemberApiDocs,
        GetLeagueBannerApiDocs {

    private final LeagueService leagueService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<GetRankResponse>> addLeague(
            @FindMember Member member,
            @RequestParam(value = "difficulty") String difficulty
    ) {
        return BaseResponse.toResponseEntityContainsResult(leagueService.addLeague(member, difficulty));
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<GetRankResponse>> changeLeague(
            @FindMember Member member,
            @RequestParam(value = "difficulty") String difficulty
    ) {
        return BaseResponse.toResponseEntityContainsResult(leagueService.changeLeague(member, difficulty));
    }

    @GetMapping("/banner")
    public ResponseEntity<BaseResponse<GetLeagueBannerResponse>> getLeagueBanner(
            @FindMember Member member
    ) {
        return BaseResponse.toResponseEntityContainsResult(leagueService.getLeagueBanner());
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
