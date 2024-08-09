package clpetition.backend.record.docs.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public interface AddRecordRequestSchema {

    @Schema(description = "기록 날짜", example = "2024-08-09")
    String getDate();

    @Schema(description = "암장 ID", example = "1")
    Long getGymId();

    @Schema(description = "난이도 정보", example = """
                                                [
                                                    {
                                                        "주황": 2
                                                    },
                                                    {
                                                        "초록": 1
                                                    }
                                                ]
                                                """)
    List<Map<String, Integer>> getDifficulties();

    @Schema(description = "운동 시간", example = "01:30")
    String getExerciseTime();

    @Schema(description = "메모", example = "기록 저장 테스트")
    String getMemo();

    @Schema(description = "만족도", example = "4")
    Integer getSatisfaction();

    @Schema(description = "비공개 여부", example = "true")
    Boolean getIsPrivate();
}
