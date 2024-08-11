package clpetition.backend.gym.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "암장 상세조회 응답")
public interface GetGymDetailsResponseSchema {

    @Schema(description = "암장 ID", example = "10")
    Long id();

    @Schema(description = "암장 브랜드명", example = "더클라임")
    String brand();

    @Schema(description = "암장 지점명", example = "연남점")
    String branch();
}
