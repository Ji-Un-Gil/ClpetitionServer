package clpetition.backend.member.dto.request;

import clpetition.backend.member.docs.dto.SocialLoginRequestSchema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SocialLoginRequest(
        @NotBlank(message = "소셜 식별자(ID)는 필수입니다")
        String socialId,
        @NotBlank(message = "소셜 타입(apple, google, kakao)은 필수입니다")
        String socialType,
        String name,
        String nickname,
        String email,
        String phoneNumber,
        String profileImage,
        String fcmToken
) implements SocialLoginRequestSchema {
}
