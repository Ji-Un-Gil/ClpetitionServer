package clpetition.backend.league.dto.response;

import clpetition.backend.league.docs.dto.response.GetLeagueRankMemberResponseSchema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetLeagueRankMemberResponse(
        List<GetLeagueRankResponse> getLeagueRankResponseList,
        Integer targetSend
) implements GetLeagueRankMemberResponseSchema {
}
