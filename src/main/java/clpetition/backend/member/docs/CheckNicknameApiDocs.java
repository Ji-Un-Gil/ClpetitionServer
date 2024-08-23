package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.annotation.NicknamePattern;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Member API", description = "사용자 API")
public interface CheckNicknameApiDocs {

    @Operation(summary = "닉네임 적합성 검증 API", description = "입력된 닉네임이 사용 가능한지 검증합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "409", description = "❌ 중복되는 닉네임이 존재",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "AUTH_003",
                                                "message": "중복된 닉네임이 존재합니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<Void>> checkNickname(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "검증할 닉네임", example = "위즈")
            @RequestParam("nickname")
            @NotBlank(message = "닉네임을 입력해주세요")
            @Length(max = 10, message = "닉네임은 10글자 이하로 작성해주세요")
            @NicknamePattern
            String nickname
    );
}
