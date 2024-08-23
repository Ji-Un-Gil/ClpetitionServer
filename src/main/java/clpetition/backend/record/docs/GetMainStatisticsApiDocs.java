package clpetition.backend.record.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.annotation.LocalDatePattern;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.dto.response.GetMainStatisticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Main API", description = "홈 화면 API")
public interface GetMainStatisticsApiDocs {

    @Operation(summary = "홈 등반 통계 조회", description = "홈 화면 등반 통계 정보를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<GetMainStatisticsResponse>> getMainStatistics(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "탐색할 연도-월", example = "2024-8")
            @LocalDatePattern(pattern = "yyyy-M") @PathVariable("yearMonth") String yearMonth
    );
}
