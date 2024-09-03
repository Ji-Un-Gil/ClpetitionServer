package clpetition.backend.league.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.league.docs.GetMainLeagueApiDocs;
import clpetition.backend.league.dto.response.GetMainLeagueResponse;
import clpetition.backend.league.service.LeagueService;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainLeagueController implements
        GetMainLeagueApiDocs {

    private final LeagueService leagueService;

    @GetMapping("/league")
    public ResponseEntity<BaseResponse<GetMainLeagueResponse>> getMainLeague(
            @FindMember Member member
    ) {
        return BaseResponse.toResponseEntityContainsResult(leagueService.getMainLeague(member));
    }
}
