package clpetition.backend.follow.service;

import clpetition.backend.follow.domain.Follow;
import clpetition.backend.follow.repository.FollowRepository;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.service.FindMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FindMemberService findMemberService;
    private final FollowRepository followRepository;

    /**
     * 팔로우 추가
     * */
    public void addFollow(Member following, Long followerId) {
        if (following.getId().equals(followerId))
            throw new BaseException(BaseResponseStatus.FOLLOWER_FOLLOWING_SAME_ERROR);

        Member follower = getFollower(followerId);
        addFollow(following, follower);
    }

    /**
     * 팔로우 취소
     * */
    public void deleteFollow(Member following, Long followerId) {
        if (following.getId().equals(followerId))
            throw new BaseException(BaseResponseStatus.FOLLOWER_FOLLOWING_SAME_ERROR);

        Member follower = getFollower(followerId);
        Follow follow = getFollow(following, follower);
        deleteFollow(follow);
    }

    /**
     * 팔로우 및 팔로잉 취소 (회원탈퇴)
     * */
    public void deleteFollowAll(Member member) {
        followRepository.deleteByFollowerOrFollowing(member, member);
    }

    /**
     * 팔로워, 팔로잉 수 가져오기
     * */
    public Map<String, Long> getFollowCount(Member member) {
        Map<String, Long> followCount = new HashMap<>();
        followCount.put("follower", getFollowerCount(member));
        followCount.put("following", getFollowingCount(member));
        return followCount;
    }

    /**
     * 팔로워 가져오기
     * */
    private Member getFollower(Long followerId) {
        return findMemberService.getMember(followerId);
    }

    /**
     * (C) 팔로우 추가
     * */
    private void addFollow(Member following, Member follower) {
        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);
    }

    /**
     * (D) 팔로우 객체 가져오기
     * */
    private Follow getFollow(Member following, Member follower) {
        return followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FOLLOW_NOT_FOUND_ERROR));
    }

    /**
     * (D) 팔로우 객체 삭제 (hard delete)
     * */
    private void deleteFollow(Follow follow) {
        followRepository.deleteById(follow.getId());
    }

    /**
     * (R) 팔로워 수 가져오기
     * */
    private Long getFollowerCount(Member member) {
        return followRepository.countFollower(member);
    }

    /**
     * (R) 팔로잉 수 가져오기
     * */
    private Long getFollowingCount(Member member) {
        return followRepository.countFollowing(member);
    }
}
