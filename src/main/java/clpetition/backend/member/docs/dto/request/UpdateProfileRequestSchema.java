package clpetition.backend.member.docs.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 수정 요청")
public interface UpdateProfileRequestSchema {

    @Schema(description = "닉네임", example = "위즈", nullable = true)
    String nickname();

    @Schema(description = "대표 암장 ID", example = "5", nullable = true)
    Long mainGymId();

    @Schema(description = "클라이밍 시작일", example = "2024-7-1", nullable = true)
    String startDate();

    @Schema(description = "생년월일", example = "2000-1-1", nullable = true)
    String birthDate();

    @Schema(description = "성별", example = "남자", nullable = true)
    String gender();

    @Schema(description = "키", example = "170", nullable = true)
    Integer height();

    @Schema(description = "리치", example = "150", nullable = true)
    Integer reach();

    @Schema(description = "인스타그램 주소", example = "https://www.instagram.com/example/", nullable = true)
    String instagram();
}
