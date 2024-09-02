package clpetition.backend.league.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "League API", description = "이달의리그 API")
public interface GetLeagueRankMemberApiDocs {

    @Operation(summary = "이달의리그 내 순위 조회 API", description = "이달의리그 특정 난이도의 내 주변 순위를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "404", description = "❌ 입력받은 난이도의 리그에 참여하고 있지 않은 사용자일 때",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "LEAGUE_002",
                                                "message": "해당 리그에 참여하고 있지 않습니다.",
                                                "result": null
                                            }
                                            """
                                    )

                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<GetLeagueRankMemberResponse>> getLeagueRankMember(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "선택할 난이도", example = "노랑")
            @RequestParam(value = "difficulty") String difficulty
    );
}
