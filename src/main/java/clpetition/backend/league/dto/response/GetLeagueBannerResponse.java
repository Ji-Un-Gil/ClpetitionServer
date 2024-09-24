package clpetition.backend.league.dto.response;

import clpetition.backend.league.docs.dto.response.GetLeagueBannerResponseSchema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetLeagueBannerResponse(
        List<String> banner
) implements GetLeagueBannerResponseSchema {
}
