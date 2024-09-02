package clpetition.backend.league.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "League API", description = "이달의리그 API")
public interface ChangeLeagueApiDocs {

    @Operation(summary = "이달의리그 난이도 변경 API", description = "이달의리그에 난이도를 변경해 참여합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<Void>> changeLeague(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "선택할 난이도", example = "노랑")
            @RequestParam(value = "difficulty") String difficulty
    );
}
