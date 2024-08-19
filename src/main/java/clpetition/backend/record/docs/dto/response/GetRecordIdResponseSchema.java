package clpetition.backend.record.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "등반 기록 ID 응답")
public interface GetRecordIdResponseSchema {

    @Schema(description = "등반 기록 ID", example = "3")
    Long getRecordId();
}
