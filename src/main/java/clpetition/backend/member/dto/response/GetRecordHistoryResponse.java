package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.GetRecordHistoryResponseSchema;
import clpetition.backend.record.domain.Difficulties;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
public class GetRecordHistoryResponse implements GetRecordHistoryResponseSchema {

    private Long recordId;

    private String gymName;

    private LocalTime exerciseTime;

    private LocalDate date;

    private Map<String, Integer> difficulties;

    private String thumbnail;

    public static Map<String, Integer> convertDifficulties(List<Difficulties> difficulties) {
        return difficulties.stream()
                .collect(Collectors.toMap(
                        difficulty -> difficulty.getDifficulty().getKey(),
                        Difficulties::getValue
                ));
    }
}
