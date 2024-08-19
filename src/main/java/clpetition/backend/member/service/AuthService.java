package clpetition.backend.member.service;

import clpetition.backend.member.dto.request.AddMemberAgreementRequest;
import clpetition.backend.member.dto.request.SocialLoginRequest;
import clpetition.backend.member.dto.response.SocialLoginResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.Role;
import clpetition.backend.member.domain.SocialType;
import clpetition.backend.member.repository.MemberRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final JwtService jwtService;
    private final FcmTokenService fcmTokenService;
    private final MemberRepository memberRepository;
    private StringBuilder stringBuilder;
    private static final Integer RANDOM_NICKNAME_LENGTH = 3;

    public SocialLoginResponse socialLogin(SocialLoginRequest socialLoginRequest) {
        if (memberRepository.existsBySocialTypeAndSocialId(SocialType.valueOf(socialLoginRequest.getSocialType()), socialLoginRequest.getSocialId())) {
            Member member = isRegister(socialLoginRequest);
            String refreshToken = jwtService.createRefreshToken();
            jwtService.updateRefreshToken(member.getId(), refreshToken);
            // fcmTokenService.saveFcmToken(member.getId(), socialLoginRequest.fcmToken());

            // 유저 정보, accesstoken, refreshtoken 전달 필요
            if (member.getRole().equals(Role.USER)) {
                return SocialLoginResponse.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .name(member.getName())
                        .nickname(member.getNickname())
                        .phoneNumber(member.getPhoneNumber())
                        .profileImage(member.getProfileImage())
                        .marketingAgree(member.getMarketingAgree())
                        .role(member.getRole().getDescription())
                        .socialType(member.getSocialType().name())
                        .socialId(member.getSocialId())
                        .accessToken(jwtService.createAccessToken(member.getSocialId()))
                        .refreshToken(refreshToken)
                        .build();
            }
            return SocialLoginResponse.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .role(member.getRole().getDescription())
                    .accessToken(jwtService.createAccessToken(member.getSocialId()))
                    .refreshToken(refreshToken)
                    .build();
        }

        Member member = isRegister(socialLoginRequest);
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(member.getId(), refreshToken);

        return SocialLoginResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .accessToken(jwtService.createAccessToken(member.getSocialId()))
                .refreshToken(refreshToken)
                .build();
    }

    public void addMemberAgreement(Member member, AddMemberAgreementRequest addMemberAgreementRequest) {
        member.updateAgreement(addMemberAgreementRequest.marketingAgree(), addMemberAgreementRequest.pushAgree());
        memberRepository.save(member);
    }

    public boolean checkNickname(Member member, String nickname) {
        if (member.getRole().equals(Role.USER) && member.getNickname().equals(nickname))
            return false;
        return memberRepository.existsByNicknameAndRole(nickname, Role.USER).equals(true);
    }

    public void logout(Member member) {
        jwtService.deleteRefreshToken(member.getId());
        // fcmTokenService.deleteFcmToken(member.getId());
    }

    public void addFcmToken(Member member, String fcmToken) {
        fcmTokenService.saveFcmToken(member.getId(), fcmToken);
    }

    // 분기 처리, 유저 정보 반환
    private Member isRegister(SocialLoginRequest socialLoginRequest) {
        SocialType socialType = SocialType.valueOf(socialLoginRequest.getSocialType().toUpperCase());
        Optional<Member> user = memberRepository.findBySocialTypeAndSocialId(socialType, socialLoginRequest.getSocialId());
        // 재로그인
        if (user.isPresent()) {
            return user.get();
        }
        // 회원가입
        Member newMember = createUser(socialLoginRequest.getSocialId(), socialType, socialLoginRequest.getName(), socialLoginRequest.getNickname(),
                socialLoginRequest.getEmail(), socialLoginRequest.getPhoneNumber(), socialLoginRequest.getProfileImage());
        memberRepository.save(newMember);
        return newMember;
    }

    private Member createUser(String socialId, SocialType socialType, String name, String nickname, String email, String phoneNumber, String profileImage) {
        // 닉네임 이모티콘, 특수기호 존재 시 삭제
        EmojiParser.removeAllEmojis(nickname);
        nickname = nickname.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");

        // 닉네임 6자리 이상 시, 5자리로 자르기
        if (nickname.length() > 5) {
            nickname = nickname.substring(0, 5);
        }

        // 분기 처리, 닉네임 중복 또는 공백 시 뒤에 랜덤값 추가
        if (nickname.isBlank() || checkNickname(nickname))
            nickname = createTemporaryNickname(nickname);

        // "람쥐" 추가
        stringBuilder = new StringBuilder();
        nickname = stringBuilder.append(nickname).append("람쥐").toString();

        return Member.builder()
                .socialId(socialId)
                .socialType(socialType)
                .name(name)
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .profileImage(profileImage)
                .role(Role.USER)
                .build();
    }

    private String createTemporaryNickname(String nickname) {
        stringBuilder = new StringBuilder();
        return stringBuilder
                .append(nickname)
                .append(RandomStringUtils.randomAlphabetic(RANDOM_NICKNAME_LENGTH))
                .toString();
    }

    private boolean checkNickname(String nickname) {
        return memberRepository.existsByNicknameAndRole(nickname, Role.USER).equals(true);
    }
}