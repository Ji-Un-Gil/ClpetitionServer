package clpetition.backend.gym.repository;

import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.domain.QGym;
import clpetition.backend.gym.dto.response.GetTargetGymListResponse;
import clpetition.backend.member.domain.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static clpetition.backend.gym.domain.QGym.gym;
import static clpetition.backend.record.domain.QRecord.record;

@Transactional
@RequiredArgsConstructor
public class GymQueryRepositoryImpl implements GymQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GetTargetGymListResponse> findGymsByMemberAndGymName(Member member, String gymName) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        GetTargetGymListResponse.class,
                        gym.id.as("gymId"),
                        gym.name.as("gymName"),
                        gym.address.as("address"),
                        record.isNotNull().as("isVisited")
                ))
                .from(gym)
                .leftJoin(record).on(
                        record.member.eq(member)
                                .and(record.gym.eq(gym))
                )
                .where(gym.name.contains(gymName))
                .orderBy(
                        record.date.desc().nullsLast(),
                        gym.name.asc()
                )
                .fetch();
    }

    @Override
    public void plusFavoritesCount(Gym gym) {
        jpaQueryFactory.update(QGym.gym)
                .where(QGym.gym.id.eq(gym.getId()))
                .set(QGym.gym.favorites, QGym.gym.favorites.add(1))
                .execute();
    }

    @Override
    public void minusFavoritesCount(Gym gym) {
        jpaQueryFactory.update(QGym.gym)
                .where(QGym.gym.id.eq(gym.getId()))
                .set(QGym.gym.favorites, QGym.gym.favorites.subtract(1))
                .execute();
    }
}
