package clpetition.backend.gym.docs;

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
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Gym API", description = "암장 API")
public interface DeleteFavoriteGymApiDocs {

    @Operation(summary = "암장 관심 삭제", description = "사용자의 관심 암장에서 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "404",
                            description =
                                    """
                                    ❌ 1) 입력받은 암장 ID가 DB에 존재하지 않음 <br/>
                                    ❌ 2) 입력받은 암장이 사용자의 관심 암장이 아님
                                    """,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "❌ 1) 입력받은 암장 ID가 DB에 존재하지 않음",
                                                    value = """
                                                            {
                                                                "code": "GYM_001",
                                                                "message": "존재하지 않는 암장입니다.",
                                                                "result": null
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "❌ 2) 입력받은 암장이 사용자의 관심 암장이 아님",
                                                    value = """
                                                            {
                                                                "code": "FAVORITE_GYM_001",
                                                                "message": "해당 암장은 관심 등록이 되어있지 않습니다.",
                                                                "result": null
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<Void>> deleteFavoriteGym(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "암장 ID", example = "2")
            @PathVariable("gymId") Long gymId
    );
}
