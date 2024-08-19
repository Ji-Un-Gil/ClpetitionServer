package clpetition.backend.record.dto.response;

import clpetition.backend.record.docs.dto.response.GetRecordIdResponseSchema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetRecordIdResponse implements GetRecordIdResponseSchema {
    private Long recordId;
}
