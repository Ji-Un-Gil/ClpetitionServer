package clpetition.backend.league.dto.response;

import clpetition.backend.league.docs.dto.response.GetLeagueRankResponseSchema;
import lombok.Builder;

@Builder(toBuilder = true)
public record GetLeagueRankResponse(
        String rank,
        Long memberId,
        String profileImageUrl,
        String nickname,
        Integer totalDay,
        Integer totalSend,
        Integer totalHour
) implements GetLeagueRankResponseSchema {
}
