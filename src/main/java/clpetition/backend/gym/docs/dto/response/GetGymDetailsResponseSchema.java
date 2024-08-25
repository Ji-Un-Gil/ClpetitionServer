package clpetition.backend.gym.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "암장 상세조회 응답")
public interface GetGymDetailsResponseSchema {

    @Schema(description = "암장 ID", example = "10")
    Long gymId();

    @Schema(description = "암장명", example = "더클라임 연남점")
    String gymName();

    @Schema(description = "암장 약어 (5글자 초과 시 ..으로 표기)", example = "더클 연남", nullable = true)
    String initial();
}
