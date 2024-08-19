package clpetition.backend.record.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관련 암장 기록 조회 응답")
public interface GetRelatedRecordResponseSchema {

    @Schema(description = "등반 기록 ID", example = "3")
    Long recordId();

    @Schema(description = "대표 이미지", example = "url1")
    String thumbnail();
}
