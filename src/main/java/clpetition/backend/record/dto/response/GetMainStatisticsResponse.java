package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetMainStatisticsResponseSchema;
import lombok.Builder;

@Builder
public record GetMainStatisticsResponse(
        Integer monthTotalRecord,
        Integer monthTotalSend,
        Integer totalSend
) implements GetMainStatisticsResponseSchema {
}
