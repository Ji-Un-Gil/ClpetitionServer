package clpetition.backend.record.dto.request;

import clpetition.backend.global.annotation.LocalDatePattern;
import clpetition.backend.global.annotation.LocalTimePattern;
import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.record.docs.dto.request.AddRecordRequestSchema;
import clpetition.backend.record.domain.Difficulties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddRecordRequest implements AddRecordRequestSchema {
    @LocalDatePattern
    @NotBlank(message = "등반일은 필수입니다")
    String date;

    @NotNull(message = "암장은 필수입니다")
    Long gymId;

    @NotNull(message = "문제 난이도는 최소 1개 이상 선택되어야 합니다")
    Map<String, Integer> difficulties;

    @LocalTimePattern
    @Builder.Default
    String exerciseTime = "00:00";

    String memo;

    @NotNull(message = "오늘 만족도는 필수입니다")
    @Min(value = 1, message = "오늘 만족도의 범위는 1 ~ 5 입니다")
    @Max(value = 5, message = "오늘 만족도의 범위는 1 ~ 5 입니다")
    Integer satisfaction;

    @Builder.Default
    Boolean isPrivate = false;
}
