package clpetition.backend.record.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.dto.response.GetRecordDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Record API", description = "등반 기록 API")
public interface GetRecordDetailsAllApiDocs {

    @Operation(summary = "전체 등반 기록 상세조회", description = "사용자 전체 등반 기록의 세부 정보를 리스트 형태로 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<List<GetRecordDetailsResponse>>> getRecordDetailsAll(
            @Parameter(hidden = true)
            @FindMember Member member
    );
}
