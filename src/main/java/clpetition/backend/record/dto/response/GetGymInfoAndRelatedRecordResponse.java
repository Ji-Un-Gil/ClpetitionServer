package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetGymInfoAndRelatedRecordResponseSchema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetGymInfoAndRelatedRecordResponse(
        List<GetRelatedRecordResponse> relatedRecord,
        String gymName,
        String region,
        Long favorites,
        Boolean isFavorite
) implements GetGymInfoAndRelatedRecordResponseSchema {
}
