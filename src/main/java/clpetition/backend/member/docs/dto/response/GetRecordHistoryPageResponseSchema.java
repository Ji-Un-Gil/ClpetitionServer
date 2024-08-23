package clpetition.backend.member.docs.dto.response;

import clpetition.backend.member.dto.response.GetRecordHistoryResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "(마이페이지) 사용자 등반 기록 최신순 조회 page 응답")
public interface GetRecordHistoryPageResponseSchema {

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    Boolean hasNext();

    @Schema(description = "(마이페이지) 사용자 등반 기록 최신순 조회 응답")
    List<GetRecordHistoryResponse> recordHistory();
}
