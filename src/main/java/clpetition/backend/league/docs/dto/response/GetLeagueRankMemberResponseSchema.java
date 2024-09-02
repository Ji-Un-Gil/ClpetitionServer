package clpetition.backend.league.docs.dto.response;

import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "이달의리그 내 순위 조회 응답")
public interface GetLeagueRankMemberResponseSchema {

    @Schema(description = "이달의리그 순위 조회 응답")
    List<GetLeagueRankResponse> getLeagueRankResponseList();

    @Schema(description = "순위를 올리기 위해 필요한 완등 수 (순위를 더 올릴 필요가 없는 경우 -1)", example = "-1")
    Integer targetSend();
}
