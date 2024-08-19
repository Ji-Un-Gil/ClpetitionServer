package clpetition.backend.member.docs.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "소셜 로그인 요청")
public interface SocialLoginRequestSchema {

    @Schema(description = "소셜 식별자(ID)", example = "123456789")
    String getSocialId();

    @Schema(description = "소셜 타입", example = "GOOGLE")
    String getSocialType();

    @Schema(description = "이름", example = "김철수")
    String getName();

    @Schema(description = "닉네임", example = "클린이")
    String getNickname();

    @Schema(description = "이메일", example = "cheolsu@gmail.com")
    String getEmail();

    @Schema(description = "휴대전화 번호", example = "010-0101-0101")
    String getPhoneNumber();

    @Schema(description = "프로필 이미지", example = "testprofileimage.png")
    String getProfileImage();

    @Schema(description = "fcm 토큰", example = "asdasdasd")
    String getFcmToken();
}
