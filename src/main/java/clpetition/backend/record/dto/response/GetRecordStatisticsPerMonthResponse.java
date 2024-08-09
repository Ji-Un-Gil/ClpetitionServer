package clpetition.backend.record.dto.response;

import lombok.Builder;

@Builder
public record GetRecordStatisticsPerMonthResponse(
        Integer totalDay,
        Double totalHour,
        Integer totalSend,
        Integer totalGym
) {
}
