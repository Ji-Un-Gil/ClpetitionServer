package clpetition.backend.appVersion.repository;

import clpetition.backend.appVersion.domain.AppType;
import clpetition.backend.appVersion.domain.AppVersion;

import java.util.List;

public interface AppVersionQueryRepository {
    List<AppVersion> findByLatestAppVersion(AppType appType);
}
