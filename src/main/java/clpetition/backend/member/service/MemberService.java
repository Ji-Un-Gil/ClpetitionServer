package clpetition.backend.member.service;

import clpetition.backend.follow.service.FollowService;
import clpetition.backend.global.infra.file.FileService;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.Profile;
import clpetition.backend.member.domain.Role;
import clpetition.backend.member.dto.request.UpdateProfileRequest;
import clpetition.backend.member.dto.response.GetProfileDetailsResponse;
import clpetition.backend.member.dto.response.GetProfileResponse;
import clpetition.backend.member.dto.response.GetRecordHistoryPageResponse;
import clpetition.backend.member.repository.MemberRepository;
import clpetition.backend.member.repository.ProfileRepository;
import clpetition.backend.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final RecordService recordService;
    private final FollowService followService;
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    private final String IMAGE_DIR = "member";

    /**
     * 닉네임 검증
     * */
    @Transactional(readOnly = true)
    public boolean checkNickname(Member member, String nickname) {
        if (member.getRole().equals(Role.USER) && member.getNickname().equals(nickname))
            return false;
        return memberRepository.existsByNicknameAndRole(nickname, Role.USER).equals(true);
    }

    /**
     * 프로필 가져오기
     * */
    public GetProfileResponse getProfile(Member member) {
        Map<String, Long> follow = followService.getFollowCount(member);
        Profile profile = findProfile(member);
        Long totalRecord = recordService.getTotalRecord(member);
        return toGetProfileResponse(member, follow, profile, totalRecord);
    }

    /**
     * 프로필 상세정보 가져오기
     * */
    @Transactional(readOnly = true)
    public GetProfileDetailsResponse getProfileDetails(Member member) {
        Profile profile = findProfile(member);
        return toGetProfileDetailsResponse(member, profile);
    }

    /**
     * 프로필 수정
     * */
    public void updateProfile(Member member, UpdateProfileRequest updateProfileRequest, MultipartFile multipartFile) throws IOException {
        String imageUrl = updateProfileImage(member, multipartFile);
        updateNicknameAndProfileImage(member, updateProfileRequest.nickname(), imageUrl);
        Profile profile = findProfile(member);
        updateProfile(profile, updateProfileRequest);
    }

    /**
     * 사용자의 등반기록 최신순으로 가져오기
     * */
    public GetRecordHistoryPageResponse getRecordHistory(Member member, Long lastRecordId) {
        return recordService.getRecordHistory(member, lastRecordId);
    }

    /**
     * (R, U) 프로필 가져오기
     * */
    private Profile findProfile(Member member) {
        return profileRepository.findByMember(member)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PROFILE_NOT_FOUND_ERROR));
    }

    /**
     * (R) 프로필 가져오기 to dto
     * */
    private GetProfileResponse toGetProfileResponse(Member member, Map<String, Long> follow, Profile profile, Long totalRecord) {
        return GetProfileResponse.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .followerCount(follow.get("follower"))
                .followingCount(follow.get("following"))
                .mainGymName(profile.getMainGym() != null ? profile.getMainGym().getName() : null)
                .height(profile.getHeight())
                .reach(profile.getReach())
                .totalRecord(totalRecord)
                .startDate(profile.getStartDate())
                .instagram(profile.getInstagram())
                .build();
    }

    /**
     * (R) 프로필 상세정보 가져오기 to dto
     * */
    private GetProfileDetailsResponse toGetProfileDetailsResponse(Member member, Profile profile) {
        return GetProfileDetailsResponse.builder()
                .nickname(member.getNickname())
                .mainGymName(profile.getMainGym() != null ? profile.getMainGym().getName() : null)
                .startDate(profile.getStartDate())
                .birthDate(profile.getBirthDate())
                .gender(profile.getGender() != null ? profile.getGender().getKey() : null)
                .height(profile.getHeight())
                .reach(profile.getReach())
                .instagram(profile.getInstagram())
                .inviteCode(profile.getInviteCode())
                .profileImage(member.getProfileImage())
                .build();
    }

    /**
     * (U) Profile 정보 수정
     * */
    private void updateProfile(Profile profile, UpdateProfileRequest updateProfileRequest) {
        profileRepository.updateProfile(profile, updateProfileRequest);
    }

    /**
     * (U) Member 프로필 이미지 수정 S3
     * */
    private String updateProfileImage(Member member, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty())
            return null;
        // 기존 프로필 이미지 S3 삭제
        if (member.getProfileImage() != null && !member.getProfileImage().isEmpty() && fileService.isValidFile(member.getProfileImage()))
            fileService.deleteFile(member.getProfileImage());
        return fileService.upload(multipartFile, IMAGE_DIR);
    }

    /**
     * (U) Member 닉네임, 프로필 이미지 수정
     * */
    private void updateNicknameAndProfileImage(Member member, String nickname, String imageUrl) {
        memberRepository.updateNicknameAndProfileImage(member, nickname, imageUrl);
    }
}
