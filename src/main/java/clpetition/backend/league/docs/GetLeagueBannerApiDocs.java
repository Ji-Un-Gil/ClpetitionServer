package clpetition.backend.league.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.league.dto.response.GetLeagueBannerResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "League API", description = "이달의리그 API")
public interface GetLeagueBannerApiDocs {

    @Operation(summary = "이달의리그 배너 리스트 조회 API", description = "이달의리그 배너 리스트를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<GetLeagueBannerResponse>> getLeagueBanner(
            @Parameter(hidden = true)
            @FindMember Member member
    );
}
