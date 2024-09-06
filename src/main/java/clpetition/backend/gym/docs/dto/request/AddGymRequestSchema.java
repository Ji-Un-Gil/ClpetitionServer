package clpetition.backend.gym.docs.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "암장 추가 요청")
public interface AddGymRequestSchema {

    @Schema(description = "암장명", example = "더클라임 B 홍대점")
    String name();

    @Schema(description = "지역", example = "서울 마포")
    String region();

    @Schema(description = "주소", example = "서울 마포구 양화로 125")
    String address();

    @Schema(description = "암장 줄임말", example = "더클 홍대")
    String shortName();
}
