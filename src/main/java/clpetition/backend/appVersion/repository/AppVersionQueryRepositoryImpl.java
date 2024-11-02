package clpetition.backend.appVersion.repository;

import clpetition.backend.appVersion.domain.AppType;
import clpetition.backend.appVersion.domain.AppVersion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static clpetition.backend.appVersion.domain.QAppVersion.appVersion;

@Transactional
@RequiredArgsConstructor
public class AppVersionQueryRepositoryImpl implements AppVersionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AppVersion> findByLatestAppVersion(AppType appType) {
        return jpaQueryFactory
                .selectFrom(appVersion)
                .where(appVersion.appType.eq(appType))
                .orderBy(appVersion.version.desc())
                .limit(1)
                .fetch();
    }
}
