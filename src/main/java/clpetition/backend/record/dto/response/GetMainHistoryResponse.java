package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetMainHistoryResponseSchema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GetMainHistoryResponse(
        Integer totalDay,
        List<LocalDate> recordDates
) implements GetMainHistoryResponseSchema {
}
