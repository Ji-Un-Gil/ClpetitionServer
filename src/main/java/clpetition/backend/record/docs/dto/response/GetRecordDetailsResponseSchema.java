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
    Long recordId();

    @Schema(description = "암장 정보")
    GetGymDetailsResponse gym();

    @Schema(description = "기록 날짜", example = "2024-08-09")
    LocalDate date();

    @Schema(description = "기록 요일", example = "5")
    Integer weekday();

    @Schema(description = "난이도 정보", example = """
                                                {
                                                    "주황": 2,
                                                    "초록": 1
                                                }
                                                """)
    Map<String, Integer> difficulties();

    @Schema(description = "메모", example = "기록 저장 테스트", nullable = true)
    String memo();

    @Schema(description = "운동 시간", example = "01:30")
    LocalTime exerciseTime();

    @Schema(description = "만족도", example = "4")
    Integer satisfaction();

    @Schema(description = "비공개 여부", example = "true")
    Boolean isPrivate();

    @Schema(description = "첨부 이미지 URL 목록", example = """
                                                        [
                                                            "url",
                                                            "url2"
                                                        ]
                                                        """)
    List<String> imageUrls();

    @Schema(description = "사용자 ID(다른 사람 기록일 때)", example = "2", nullable = true)
    Long memberId();

    @Schema(description = "프로필 사진 URL(다른 사람 기록일 때)", example = "url", nullable = true)
    String profileImageUrl();

    @Schema(description = "닉네임(다른 사람 기록일 때)", example = "위즈", nullable = true)
    String nickname();

    @Schema(description = "리그 참여 난이도(다른 사람 기록일 때)", example = "주황", nullable = true)
    String difficulty();

    @Schema(description = "리그 순위(다른 사람 기록일 때), Integer가 아닌 String임을 주의", example = "17", nullable = true)
    String rank();
}
