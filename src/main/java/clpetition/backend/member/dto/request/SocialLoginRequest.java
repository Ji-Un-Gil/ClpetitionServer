package clpetition.backend.member.dto.request;

import clpetition.backend.member.docs.dto.request.SocialLoginRequestSchema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginRequest implements SocialLoginRequestSchema {

        @NotBlank(message = "소셜 식별자(ID)는 필수입니다")
        private String socialId;

        @NotBlank(message = "소셜 타입(apple, google, kakao)은 필수입니다")
        private String socialType;

        private String name;

        @Builder.Default
        private String nickname = "";

        private String email;

        private String phoneNumber;

        private String profileImage;

        private String fcmToken;
}