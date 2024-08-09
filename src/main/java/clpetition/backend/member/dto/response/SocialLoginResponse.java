package clpetition.backend.member.dto.response;

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
) {
}
