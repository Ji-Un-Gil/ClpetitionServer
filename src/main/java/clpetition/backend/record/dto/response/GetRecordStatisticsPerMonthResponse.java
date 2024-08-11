package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetRecordStatisticsPerMonthResponseSchema;
import lombok.Builder;

@Builder
public record GetRecordStatisticsPerMonthResponse(
        Integer totalDay,
        Double totalHour,
        Integer totalSend,
        Integer totalGym
) implements GetRecordStatisticsPerMonthResponseSchema {
}
