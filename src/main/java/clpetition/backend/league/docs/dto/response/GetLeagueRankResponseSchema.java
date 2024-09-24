package clpetition.backend.league.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이달의리그 순위 조회 응답")
public interface GetLeagueRankResponseSchema {

    @Schema(description = "순위 (공동 순위 시 T5 와 같이 반환), Integer가 아닌 String임을 주의", example = "1")
    String rank();

    @Schema(description = "사용자 ID", example = "3")
    Long memberId();

    @Schema(description = "프로필 이미지 URL", example = "url", nullable = true)
    String profileImageUrl();

    @Schema(description = "닉네임", example = "위즈")
    String nickname();

    @Schema(description = "등반일", example = "2")
    Integer totalDay();

    @Schema(description = "완등", example = "5")
    Integer totalSend();

    @Schema(description = "총 시간", example = "3")
    Integer totalHour();
}
