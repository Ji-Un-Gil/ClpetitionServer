package clpetition.backend.appVersion.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "앱 최신 버전 조회 응답")
public interface GetLatestAppVersionResponseSchema {

    @Schema(description = "앱 버전", example = "1.1.0")
    String version();
}
