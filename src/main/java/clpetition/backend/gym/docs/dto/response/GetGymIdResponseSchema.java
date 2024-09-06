package clpetition.backend.gym.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "암장 추가 응답")
public interface GetGymIdResponseSchema {

    @Schema(description = "암장 ID", example = "11")
    Long gymId();
}
