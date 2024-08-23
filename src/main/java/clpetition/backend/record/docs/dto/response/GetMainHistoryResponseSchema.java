package clpetition.backend.record.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "홈 월 등반 내역 조회 응답")
public interface GetMainHistoryResponseSchema {

    @Schema(description = "총 등반 일수", example = "12")
    Integer totalDay();

    @Schema(description = "등반 기록일자 목록", example = """
                                                        [
                                                            "2024-08-03",
                                                            "2024-08-07"
                                                        ]
                                                        """)
    List<LocalDate> recordDates();
}
