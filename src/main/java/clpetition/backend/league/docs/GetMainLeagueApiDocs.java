package clpetition.backend.league.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.league.dto.response.GetMainLeagueResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Main API", description = "홈 화면 API")
public interface GetMainLeagueApiDocs {

    @Operation(summary = "홈 이달의리그 정보 조회 API", description = "홈 화면의 이달의리그 관련 정보를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<GetMainLeagueResponse>> getMainLeague(
            @Parameter(hidden = true)
            @FindMember Member member
    );
}
