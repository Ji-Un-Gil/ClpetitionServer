package clpetition.backend.record.docs.dto.response;

import clpetition.backend.gym.dto.response.GetGymDetailsResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Schema(description = "등반 기록 상세조회 응답")
public interface GetRecordDetailsResponseSchema {

    @Schema(description = "등반 기록 ID", example = "3")
    Long getRecordId();

    @Schema(description = "암장 정보")
    GetGymDetailsResponse getGym();

    @Schema(description = "기록 날짜", example = "2024-08-09")
    LocalDate getDate();

    @Schema(description = "기록 요일", example = "2024-08-09")
    Integer getWeekday();

    @Schema(description = "난이도 정보", example = """
                                                {
                                                    "주황": 2,
                                                    "초록": 1
                                                }
                                                """)
    Map<String, Integer> getDifficulties();

    @Schema(description = "메모", example = "기록 저장 테스트")
    String getMemo();

    @Schema(description = "운동 시간", example = "01:30")
    LocalTime getExerciseTime();

    @Schema(description = "만족도", example = "4")
    Integer getSatisfaction();

    @Schema(description = "비공개 여부", example = "true")
    Boolean getIsPrivate();

    @Schema(description = "첨부 이미지 URL 목록", example = """
                                                        [
                                                            "url",
                                                            "url2"
                                                        ]
                                                        """)
    List<String> getImageUrls();
}
