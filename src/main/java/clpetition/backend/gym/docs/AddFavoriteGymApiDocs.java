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
public interface AddFavoriteGymApiDocs {

    @Operation(summary = "암장 관심 등록", description = "사용자의 관심 암장으로 등록합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "404", description = "❌ 입력받은 암장 ID가 DB에 존재하지 않음",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "GYM_001",
                                                "message": "존재하지 않는 암장입니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<BaseResponse<Void>> addFavoriteGym(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "암장 ID", example = "2")
            @PathVariable("gymId") Long gymId
    );
}
