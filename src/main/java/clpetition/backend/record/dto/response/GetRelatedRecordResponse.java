package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetRelatedRecordResponseSchema;
import lombok.Builder;

@Builder
public record GetRelatedRecordResponse(
        Long recordId,
        String thumbnail
) implements GetRelatedRecordResponseSchema {
}
