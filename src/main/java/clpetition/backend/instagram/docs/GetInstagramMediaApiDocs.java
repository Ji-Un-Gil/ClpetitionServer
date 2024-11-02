package clpetition.backend.instagram.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.instagram.dto.response.GetInstagramMediaResponse;
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
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Instagram API", description = "인스타그램 API")
public interface GetInstagramMediaApiDocs {

    @Operation(summary = "인스타그램 사용자 미디어 조회 API", description = "인스타그램 사용자 미디어를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "401", description = "❌ 인스타그램 로그인이 안 되어 있는 사용자로 조회 시도",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "INSTAGRAM_001",
                                                "message": "인스타그램 로그인 후 이용해주세요.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    ),
            }
    )
    @GetMapping("/media")
    ResponseEntity<BaseResponse<GetInstagramMediaResponse>> getInstagramMedia(
            @Parameter(hidden = true)
            @FindMember Member member
    );
}
