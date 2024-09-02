package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth API", description = "사용자 인증 API")
public interface DeleteMemberApiDocs {

    @Operation(summary = "사용자 회원탈퇴", description = "사용자 정보를 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<Void>> deleteMember(
            @Parameter(hidden = true)
            @FindMember Member member
    );
}
