package clpetition.backend.member.domain;

import clpetition.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String phoneNumber;

    private String profileImage;

    private Boolean marketingAgree;

    private Boolean pushAgree;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId; // 로그인한 소셜 타입의 식별자 값

    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void addDetails(String nickname, Boolean marketingAgree) {
        this.nickname = nickname;
        this.marketingAgree = marketingAgree;
        this.pushAgree = true;
        this.role = Role.USER;
    }

    public void updateDetails(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateAgreement(Boolean marketingAgree, Boolean pushAgree) {
        this.marketingAgree = marketingAgree;
        this.pushAgree = pushAgree;
    }
}
