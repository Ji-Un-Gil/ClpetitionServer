package clpetition.backend.member.repository;

import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.QMember;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateNicknameAndProfileImage(Member member, String nickname, String imageUrl) {
        QMember qMember = QMember.member;

        jpaQueryFactory.update(qMember)
                .where(qMember.eq(member))
                .set(qMember.nickname, nickname != null ? Expressions.constant(nickname) : qMember.nickname)
                .set(qMember.profileImage, imageUrl != null ? Expressions.constant(imageUrl) : qMember.profileImage)
                .execute();
    }
}
