package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.response.GetRecordHistoryPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Member API", description = "사용자 API")
public interface GetRecordHistoryApiDocs {

    @Operation(summary = "마이페이지 등반기록 히스토리 조회 API", description = "등반기록을 최근 등반일순으로 10개씩 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<GetRecordHistoryPageResponse>> getRecordHistory(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "페이지의 마지막 등반 기록 ID", example = "6")
            @RequestParam(value = "lastRecordId", required = false) Long lastRecordId
    );
}
