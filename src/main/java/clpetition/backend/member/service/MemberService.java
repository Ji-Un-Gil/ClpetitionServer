package clpetition.backend.member.service;

import clpetition.backend.follow.service.FollowService;
import clpetition.backend.global.infra.file.FileService;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.league.service.LeagueService;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.Profile;
import clpetition.backend.member.domain.Role;
import clpetition.backend.member.dto.request.UpdateProfileRequest;
import clpetition.backend.member.dto.response.GetProfileResponse;
import clpetition.backend.member.dto.response.GetRecordHistoryPageResponse;
import clpetition.backend.member.dto.response.UpdateProfileResponse;
import clpetition.backend.member.repository.MemberRepository;
import clpetition.backend.member.repository.ProfileRepository;
import clpetition.backend.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final RecordService recordService;
    private final FollowService followService;
    private final FileService fileService;
    private final FindMemberService findMemberService;
    private final LeagueService leagueService;
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
        return memberRepository.existsByNicknameAndRole(nickname, Role.USER);
    }

    /**
     * 프로필 가져오기
     * */
    @Transactional(readOnly = true)
    public GetProfileResponse getProfile(Member member, Long memberId) {
        if (memberId != null)
            member = findMemberService.getMember(memberId);

        Map<String, Long> follow = followService.getFollowCount(member);
        Profile profile = findProfile(member);
        Long totalRecord = recordService.getTotalRecord(member);
        Map<String, Object> leagueBrief = leagueService.getLeagueBrief(member);
        return toGetProfileResponse(member, follow, profile, totalRecord, leagueBrief);
    }

    /**
     * 프로필 수정
     * */
    public UpdateProfileResponse updateProfile(Member member, UpdateProfileRequest updateProfileRequest, MultipartFile multipartFile) throws IOException {
        String imageUrl = updateProfileImage(member, multipartFile);
        if (imageUrl == null)
            imageUrl = member.getProfileImage();
        updateNicknameAndProfileImage(member, updateProfileRequest.nickname(), imageUrl);
        Profile profile = findProfile(member);
        updateProfile(profile, updateProfileRequest);
        return toUpdateProfileResponse(imageUrl);
    }

    /**
     * 사용자의 등반기록 최신순으로 가져오기
     * */
    public GetRecordHistoryPageResponse getRecordHistory(Member member, Long memberId, Long lastRecordId) {
        boolean isMyself = true;
        if (memberId != null) {
            member = findMemberService.getMember(memberId);
            isMyself = false;
        }

        return recordService.getRecordHistory(member, lastRecordId, isMyself);
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
    private GetProfileResponse toGetProfileResponse(Member member, Map<String, Long> follow, Profile profile, Long totalRecord, Map<String, Object> leagueBrief) {
        return GetProfileResponse.builder()
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImage())
                .followerCount(follow.get("follower"))
                .followingCount(follow.get("following"))
                .mainGymId(profile.getMainGym() != null ? profile.getMainGym().getId() : null)
                .height(profile.getHeight())
                .reach(profile.getReach())
                .totalRecord(totalRecord)
                .startDate(profile.getStartDate())
                .birthDate(profile.getBirthDate())
                .gender(profile.getGender() != null ? profile.getGender().getKey() : null)
                .instagram(profile.getInstagram())
                .inviteCode(profile.getInviteCode())
                .difficulty(!leagueBrief.get("difficulty").equals(Optional.empty()) ? leagueBrief.get("difficulty").toString() : null)
                .rank(!leagueBrief.get("rank").equals(Optional.empty()) ? leagueBrief.get("rank").toString() : null)
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

    /**
     * (U) 프로필 수정 후 프로필 이미지 dto 반환
     * */
    private UpdateProfileResponse toUpdateProfileResponse(String imageUrl) {
        return UpdateProfileResponse.builder()
                .profileImageUrl(imageUrl)
                .build();
    }
}
