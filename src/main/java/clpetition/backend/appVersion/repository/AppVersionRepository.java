package clpetition.backend.appVersion.repository;

import clpetition.backend.appVersion.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long>, AppVersionQueryRepository {
}
