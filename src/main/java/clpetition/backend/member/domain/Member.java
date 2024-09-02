package clpetition.backend.member.domain;

import clpetition.backend.global.entity.BaseTimeEntity;
import clpetition.backend.gym.domain.FavoriteGym;
import clpetition.backend.gym.domain.VisitsGym;
import clpetition.backend.league.domain.League;
import clpetition.backend.record.domain.Record;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private List<League> leagues = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private List<Record> records = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private List<FavoriteGym> favoriteGyms = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private List<VisitsGym> visitsGyms = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private Profile profile;

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
