package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.GetRecordHistoryPageResponseSchema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetRecordHistoryPageResponse(
        Boolean hasNext,
        List<GetRecordHistoryResponse> recordHistory
) implements GetRecordHistoryPageResponseSchema {
}
