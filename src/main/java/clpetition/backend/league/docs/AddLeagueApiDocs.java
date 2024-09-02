package clpetition.backend.league.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
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
public interface AddLeagueApiDocs {

    @Operation(summary = "이달의리그 참여 API", description = "이달의리그에 최초 참여합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "409", description = "❌ 이미 이번 시즌 리그에 참여중인 사용자일 때",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "LEAGUE_001",
                                                "message": "이미 리그에 참여하고 있어 리그 등록을 할 수 없습니다.",
                                                "result": null
                                            }
                                            """
                                    )

                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<Void>> addLeague(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "선택할 난이도", example = "노랑")
            @RequestParam(value = "difficulty") String difficulty
    );
}
