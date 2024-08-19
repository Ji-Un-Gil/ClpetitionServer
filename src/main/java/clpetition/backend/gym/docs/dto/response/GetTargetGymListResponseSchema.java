package clpetition.backend.gym.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "암장 검색으로 조회 응답")
public interface GetTargetGymListResponseSchema {

    @Schema(description = "암장 ID", example = "10")
    Long gymId();

    @Schema(description = "암장명", example = "더클라임 연남점")
    String gymName();

    @Schema(description = "암장 주소", example = "인천 남동구 경인로 520")
    String address();

    @Schema(description = "암장 방문 여부", example = "true")
    Boolean isVisited();
}
