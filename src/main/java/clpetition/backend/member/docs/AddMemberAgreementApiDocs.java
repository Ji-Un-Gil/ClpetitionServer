package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.request.AddMemberAgreementRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth API", description = "사용자 인증 API")
public interface AddMemberAgreementApiDocs {

    @Operation(summary = "선택약관 동의 API", description = "선택약관 동의 여부를 저장합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<Void>> addMemberAgreement(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Valid @RequestBody AddMemberAgreementRequest addMemberAgreementRequest
    );
}
