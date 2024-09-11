package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.GetRecordHistoryPageResponseSchema;
import clpetition.backend.record.dto.response.GetRecordDetailsResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record GetRecordHistoryPageResponse(
        Boolean hasNext,
        List<GetRecordDetailsResponse> recordHistory
) implements GetRecordHistoryPageResponseSchema {
}
