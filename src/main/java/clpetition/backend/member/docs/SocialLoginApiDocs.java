package clpetition.backend.member.docs;

import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.dto.request.SocialLoginRequest;
import clpetition.backend.member.dto.response.SocialLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth API", description = "사용자 인증 API")
public interface SocialLoginApiDocs {

    @Operation(summary = "소셜 로그인 API", description = "소셜 사용자로 로그인합니다.(신규인 경우 새로 등록)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<SocialLoginResponse>> socialLogin(
            @Valid @RequestBody SocialLoginRequest socialLoginRequest
    );
}
