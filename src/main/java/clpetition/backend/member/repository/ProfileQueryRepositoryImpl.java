package clpetition.backend.member.repository;

import clpetition.backend.gym.domain.QGym;
import clpetition.backend.member.domain.Gender;
import clpetition.backend.member.domain.Profile;
import clpetition.backend.member.domain.QProfile;
import clpetition.backend.member.dto.request.UpdateProfileRequest;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@RequiredArgsConstructor
public class ProfileQueryRepositoryImpl implements ProfileQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateProfile(Profile profile, UpdateProfileRequest updateProfileRequest) {
        QProfile qProfile = QProfile.profile;
        QGym qGym = QGym.gym;

        jpaQueryFactory.update(qProfile)
                .where(qProfile.eq(profile))
                .set(qProfile.mainGym, updateProfileRequest.mainGymId() != null ?
                        jpaQueryFactory.selectFrom(qGym).where(qGym.id.eq(updateProfileRequest.mainGymId())) : qProfile.mainGym)
                .set(qProfile.startDate, updateProfileRequest.startDate() != null ?
                        Expressions.constant(LocalDate.parse(updateProfileRequest.startDate(), DateTimeFormatter.ofPattern("yyyy-M-d"))) : qProfile.startDate)
                .set(qProfile.birthDate, updateProfileRequest.birthDate() != null ?
                        Expressions.constant(LocalDate.parse(updateProfileRequest.birthDate(), DateTimeFormatter.ofPattern("yyyy-M-d"))) : qProfile.birthDate)
                .set(qProfile.gender, updateProfileRequest.gender() != null ?
                        Expressions.constant(Gender.findByKey(updateProfileRequest.gender())) : qProfile.gender)
                .set(qProfile.height, updateProfileRequest.height() != null ?
                        Expressions.constant(updateProfileRequest.height()) : qProfile.height)
                .set(qProfile.reach, updateProfileRequest.reach() != null ?
                        Expressions.constant(updateProfileRequest.reach()) : qProfile.reach)
                .set(qProfile.instagram, updateProfileRequest.instagram() != null ?
                        Expressions.constant(updateProfileRequest.instagram()) : qProfile.instagram)
                .execute();
    }
}
