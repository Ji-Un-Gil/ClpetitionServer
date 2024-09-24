package clpetition.backend.appVersion.service;

import clpetition.backend.appVersion.domain.AppVersion;
import clpetition.backend.appVersion.dto.response.GetLatestAppVersionResponse;
import clpetition.backend.appVersion.repository.AppVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppVersionService {

    private final AppVersionRepository appVersionRepository;

    /**
     * iOS 앱 버전 추가
     * */
    public void addAppVersion(String version) {
        saveAppVersion(version);
    }

    /**
     * iOS 앱 최신 버전 조회
     * */
    public GetLatestAppVersionResponse getLatestAppVersion() {
        return findByLatestAppVersion();
    }

    /**
     * iOS 앱 버전 저장
     * */
    private void saveAppVersion(String version) {
        appVersionRepository.save(
                AppVersion.builder()
                        .version(version)
                        .build()
        );
    }

    /**
     * iOS 앱 최신 버전 조회 to dto
     * */
    private GetLatestAppVersionResponse findByLatestAppVersion() {
        return GetLatestAppVersionResponse.builder()
                .version(appVersionRepository.findByLatestAppVersion().get(0).getVersion())
                .build();
    }
}
