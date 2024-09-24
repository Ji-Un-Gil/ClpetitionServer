package clpetition.backend.league.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이달의리그 추가, 변경 시 내 순위 조회 응답")
public interface GetRankResponseSchema {

    @Schema(description = "순위, Integer가 아닌 String임을 주의", example = "1")
    String rank();
}
