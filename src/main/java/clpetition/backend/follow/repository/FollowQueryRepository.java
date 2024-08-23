package clpetition.backend.follow.repository;

import clpetition.backend.member.domain.Member;

public interface FollowQueryRepository {
    Long countFollower(Member member);
    Long countFollowing(Member member);
}
