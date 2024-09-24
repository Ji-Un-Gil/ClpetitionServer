package clpetition.backend.appVersion.dto.response;

import clpetition.backend.appVersion.docs.dto.response.GetLatestAppVersionResponseSchema;
import lombok.Builder;

@Builder
public record GetLatestAppVersionResponse(
        String version
) implements GetLatestAppVersionResponseSchema {
}
