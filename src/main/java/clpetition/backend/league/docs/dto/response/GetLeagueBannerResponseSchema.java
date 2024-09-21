package clpetition.backend.league.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "이달의리그 배너 리스트 조회 응답")
public interface GetLeagueBannerResponseSchema {

    @Schema(description = "배너 리스트", example = """
                                                {
                                                    "D-30 시즌 1번째 리그 진행중",
                                                    "리그 기간 동안 99번 등수가 변동됐어요!"
                                                }
                                                """)
    List<String> banner();
}
