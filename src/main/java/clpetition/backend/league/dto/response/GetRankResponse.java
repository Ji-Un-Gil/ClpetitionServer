package clpetition.backend.league.dto.response;

import clpetition.backend.league.docs.dto.response.GetRankResponseSchema;
import lombok.Builder;

@Builder
public record GetRankResponse(
        String rank
) implements GetRankResponseSchema {
}
