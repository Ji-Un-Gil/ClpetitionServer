package clpetition.backend.appVersion.service;

import clpetition.backend.appVersion.domain.AppType;
import clpetition.backend.appVersion.domain.AppVersion;
import clpetition.backend.appVersion.dto.response.GetLatestAppVersionResponse;
import clpetition.backend.appVersion.repository.AppVersionRepository;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppVersionService {

    private final AppVersionRepository appVersionRepository;

    /**
     * 앱 버전 추가
     * */
    public void addAppVersion(String appType, String version) {
        isValidAppType(appType);
        saveAppVersion(appType, version);
    }

    /**
     * 앱 최신 버전 조회
     * */
    public GetLatestAppVersionResponse getLatestAppVersion(String appType) {
        isValidAppType(appType);
        return findByLatestAppVersion(appType);
    }

    /**
     * 앱 버전 저장
     * */
    private void saveAppVersion(String appType, String version) {
        appVersionRepository.save(
                AppVersion.builder()
                        .appType(AppType.valueOf(appType))
                        .version(version)
                        .build()
        );
    }

    /**
     * 앱 최신 버전 조회 to dto
     * */
    private GetLatestAppVersionResponse findByLatestAppVersion(String appType) {
        return GetLatestAppVersionResponse.builder()
                .version(appVersionRepository.findByLatestAppVersion(AppType.valueOf(appType)).get(0).getVersion())
                .build();
    }

    private void isValidAppType(String appType) {
        for (AppType compareAppType : AppType.values()) {
            if (appType.equals(compareAppType.name()))
                return;
        }
        throw new BaseException(BaseResponseStatus.APP_TYPE_NOT_FOUND_ERROR);
    }
}
