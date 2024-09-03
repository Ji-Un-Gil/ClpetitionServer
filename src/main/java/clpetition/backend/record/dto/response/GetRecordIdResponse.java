package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetRecordIdResponseSchema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetRecordIdResponse(
        Long recordId,
        List<String> imageUrls
) implements GetRecordIdResponseSchema {
}
