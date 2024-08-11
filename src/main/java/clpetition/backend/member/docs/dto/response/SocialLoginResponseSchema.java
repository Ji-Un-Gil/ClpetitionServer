package clpetition.backend.member.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "소셜 로그인 응답")
public interface SocialLoginResponseSchema {

    @Schema(description = "사용자 ID", example = "1")
    Long id();

    @Schema(description = "이메일", example = "cheolsu@gmail.com")
    String email();

    @Schema(description = "이름", example = "김철수")
    String name();

    @Schema(description = "닉네임", example = "클린이")
    String nickname();

    @Schema(description = "휴대전화 번호", example = "010-0101-0101")
    String phoneNumber();

    @Schema(description = "프로필 이미지", example = "testprofileimage.png")
    String profileImage();

    @Schema(description = "마케팅 수신 동의 여부", example = "false")
    Boolean marketingAgree();

    @Schema(description = "사용자 역할", example = "USER")
    String role();

    @Schema(description = "소셜 타입", example = "GOOGLE")
    String socialType();

    @Schema(description = "소셜 식별자(ID)", example = "123456789")
    String socialId();

    @Schema(description = "JWT 액세스 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInNvY2lhbElkIjoiMTIzNDU2Nzg5IiwiZXhwIjoxNzU0NzI5Mjk0fQ.1NnXV6neLEVwdxdutSskYumQC50Hyzra-tcaFlkp3of4rTw1SPpJjFGh7RzuOw45lgWlkz8pYhrKT3Rdn9AI-w")
    String accessToken();

    @Schema(description = "JWT 리프레시 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInNvY2lhbElkIjoiMTIzNDU2Nzg5IiwiZXhwIjoxNzU0NzI5Mjk0fQ.1NnXV6neLEVwdxdutSskYumQC50Hyzra-tcaFlkp3of4rTw1SPpJjFGh7RzuOw45lgWlkz8pYhrKT3Rdn9AI-w")
    String refreshToken();
}
