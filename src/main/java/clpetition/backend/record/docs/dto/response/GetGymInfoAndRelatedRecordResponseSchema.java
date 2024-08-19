package clpetition.backend.record.docs.dto.response;

import clpetition.backend.record.dto.response.GetRelatedRecordResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "관련 암장 정보 및 등반 기록 조회 응답")
public interface GetGymInfoAndRelatedRecordResponseSchema {

    @Schema(description = "관련 암장 기록 조회 응답")
    List<GetRelatedRecordResponse> relatedRecord();

    @Schema(description = "암장명", example = "더클라임 클라이밍 신림점")
    String gymName();

    @Schema(description = "암장 소재 지역명", example = "서울 관악")
    String region();

    @Schema(description = "암장 관심 수", example = "2214")
    Long favorites();

    @Schema(description = "사용자의 암장 관심 등록 여부", example = "true")
    Boolean isFavorite();
}
