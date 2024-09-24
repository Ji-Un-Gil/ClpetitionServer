package clpetition.backend.appVersion.docs;

import clpetition.backend.appVersion.dto.response.GetLatestAppVersionResponse;
import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "App Version API", description = "iOS 앱 버전 API")
public interface GetLatestAppVersionApiDocs {

    @Operation(summary = "앱 최신 버전 조회", description = "앱 최신 버전을 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<GetLatestAppVersionResponse>> getLatestAppVersion(
            @Parameter(hidden = true)
            @FindMember Member member
    );
}
