package clpetition.backend.follow.docs;

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

@Tag(name = "Follow API", description = "팔로우 API")
public interface DeleteFollowApiDocs {

    @Operation(summary = "팔로우 취소", description = "사용자의 팔로우 대상에서 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204", description = "🟢 정상 (응답 제공 X)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(
                                            value = ""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description =
                                    """
                                    ❌ 1) 입력받은 사용자 ID가 DB에 존재하지 않음 <br/>
                                    ❌ 2) 입력받은 사용자를 팔로우하고 있지 않음
                                    """,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "❌ 1) 입력받은 사용자 ID가 DB에 존재하지 않음",
                                                    value = """
                                                            {
                                                                "code": "MEMBER_001",
                                                                "message": "존재하지 않는 사용자입니다.",
                                                                "result": null
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "❌ 2) 입력받은 사용자를 팔로우하고 있지 않음",
                                                    value = """
                                                            {
                                                                "code": "FOLLOW_001",
                                                                "message": "해당 회원을 팔로우하고 있지 않습니다.",
                                                                "result": null
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409", description = "❌ 입력받은 사용자 ID가 요청하는 사용자 ID와 같음",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "FOLLOW_002",
                                                "message": "본인을 팔로우하거나 팔로우 취소할 수 없습니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<Void>> deleteFollow(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "팔로우를 취소할 사용자 ID", example = "1")
            @PathVariable("followerId") Long followerId
    );
}
