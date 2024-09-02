package clpetition.backend.member.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 수정 응답")
public interface UpdateProfileResponseSchema {

    @Schema(description = "프로필 이미지 URL", example = "url", nullable = true)
    String profileImageUrl();
}
