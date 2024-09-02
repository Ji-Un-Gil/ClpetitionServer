package clpetition.backend.league.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "League API", description = "이달의리그 API")
public interface GetLeagueRankTopFiftyApiDocs {

    @Operation(summary = "이달의리그 TOP 50 조회 API", description = "이달의리그 특정 난이도의 TOP 50을 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<List<GetLeagueRankResponse>>> getLeagueRankTopFifty(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "선택할 난이도", example = "노랑")
            @RequestParam(value = "difficulty") String difficulty
    );
}
