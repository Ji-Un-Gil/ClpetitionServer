package clpetition.backend.league.dto.response;

import clpetition.backend.league.docs.dto.response.GetMainLeagueResponseSchema;
import lombok.Builder;

@Builder
public record GetMainLeagueResponse(
        String difficulty,
        String rank,
        Integer totalSend,
        Integer gapRecentMonth
) implements GetMainLeagueResponseSchema {
}
