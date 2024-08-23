package clpetition.backend.record.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "홈 등반 통계 조회 응답")
public interface GetMainStatisticsResponseSchema {

    @Schema(description = "월 총 등반 기록 횟수", example = "15")
    Integer monthTotalRecord();

    @Schema(description = "월 총 완등 수", example = "35")
    Integer monthTotalSend();

    @Schema(description = "총 완등 수", example = "123")
    Integer totalSend();
}
