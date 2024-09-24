package clpetition.backend.league.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "홈 이달의리그 정보 조회 응답")
public interface GetMainLeagueResponseSchema {

    @Schema(description = "참여한 난이도", example = "주황", nullable = true)
    String difficulty();

    @Schema(description = "순위, Integer가 아닌 String임을 주의", example = "17", nullable = true)
    String rank();

    @Schema(description = "완등 수", example = "55", nullable = true)
    Integer totalSend();

    @Schema(description = "전월 대비 순위 차이 (음수일 경우, 전월 대비 떨어진 것을 의미)", example = "6", nullable = true)
    Integer gapRecentMonth();
}
