package clpetition.backend.member.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Schema(description = "(마이페이지) 사용자 등반 기록 최신순 조회 응답")
public interface GetRecordHistoryResponseSchema {

    @Schema(description = "등반 기록 ID", example = "3")
    Long getRecordId();

    @Schema(description = "등반 암장명", example = "더클라임 신림점")
    String getGymName();

    @Schema(description = "운동 시간", example = "01:30")
    LocalTime getExerciseTime();

    @Schema(description = "기록 날짜", example = "2024-08-09")
    LocalDate getDate();

    @Schema(description = "난이도 정보", example = """
                                                {
                                                    "주황": 2,
                                                    "초록": 1
                                                }
                                                """)
    Map<String, Integer> getDifficulties();

    @Schema(description = "대표 이미지", example = "url1")
    String getThumbnail();
}
