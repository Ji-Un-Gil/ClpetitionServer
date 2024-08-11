package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.SocialLoginResponseSchema;
import lombok.Builder;

@Builder
public record SocialLoginResponse(
        Long id,

        String email,

        String name,

        String nickname,

        String phoneNumber,

        String profileImage,

        Boolean marketingAgree,

        String role,

        String socialType,

        String socialId,

        String accessToken,

        String refreshToken
) implements SocialLoginResponseSchema {
}
