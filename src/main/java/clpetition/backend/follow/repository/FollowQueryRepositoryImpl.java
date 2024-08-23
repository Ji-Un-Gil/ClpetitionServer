package clpetition.backend.follow.repository;

import clpetition.backend.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static clpetition.backend.follow.domain.QFollow.follow;

@Transactional
@RequiredArgsConstructor
public class FollowQueryRepositoryImpl implements FollowQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countFollower(Member member) {
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.follower.eq(member))
                .fetchOne();
    }

    @Override
    public Long countFollowing(Member member) {
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.following.eq(member))
                .fetchOne();
    }
}
