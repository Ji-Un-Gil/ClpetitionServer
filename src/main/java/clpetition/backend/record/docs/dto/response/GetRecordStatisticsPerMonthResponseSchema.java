package clpetition.backend.record.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "월 등반 통계 조회 응답")
public interface GetRecordStatisticsPerMonthResponseSchema {

    @Schema(description = "총 등반 일수", example = "12")
    Integer totalDay();

    @Schema(description = "총 등반 시간", example = "32.0")
    Double totalHour();

    @Schema(description = "총 완등 수", example = "35")
    Integer totalSend();

    @Schema(description = "총 방문 암장 수", example = "2")
    Integer totalGym();
}
