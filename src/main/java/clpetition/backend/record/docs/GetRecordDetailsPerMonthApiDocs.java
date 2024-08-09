package clpetition.backend.record.docs;

import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.dto.response.GetRecordDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Record API", description = "등반 기록 API")
public interface GetRecordDetailsPerMonthApiDocs {
    @Operation(summary = "월 등반 기록 상세조회", description = "월 등반 기록의 세부 정보를 리스트 형태로 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<Map<LocalDate, List<GetRecordDetailsResponse>>>> getRecordDetailsPerMonth(
            @Parameter(hidden = true)
            Member member,

            @Parameter(description = "탐색할 연도-월", example = "2024-8")
            String yearMonth
    );
}
