package clpetition.backend.follow.repository;

import clpetition.backend.follow.domain.Follow;
import clpetition.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowQueryRepository {
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);
}
