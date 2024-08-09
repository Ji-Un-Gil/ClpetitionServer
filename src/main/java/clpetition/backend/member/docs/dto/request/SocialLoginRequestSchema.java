package clpetition.backend.member.docs.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface SocialLoginRequestSchema {

    @Schema(description = "소셜 식별자(ID)", example = "123456789")
    String socialId();

    @Schema(description = "소셜 타입", example = "GOOGLE")
    String socialType();

    @Schema(description = "이름", example = "김철수")
    String name();

    @Schema(description = "닉네임", example = "클린이")
    String nickname();

    @Schema(description = "이메일", example = "cheolsu@gmail.com")
    String email();

    @Schema(description = "휴대전화 번호", example = "010-0101-0101")
    String phoneNumber();

    @Schema(description = "프로필 이미지", example = "testprofileimage.png")
    String profileImage();

    @Schema(description = "fcm 토큰", example = "asdasdasd")
    String fcmToken();
}
