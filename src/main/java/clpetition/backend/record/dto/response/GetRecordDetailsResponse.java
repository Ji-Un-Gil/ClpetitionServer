package clpetition.backend.record.dto.response;

import clpetition.backend.gym.dto.response.GetGymDetailsResponse;
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
public class GetRecordDetailsResponse {
    private Long id;

    private GetGymDetailsResponse gym;

    private LocalDate date;

    private Integer weekday;

    private List<Map<String, Integer>> difficulties;

    private String memo;

    private LocalTime exerciseTime;

    private Integer satisfaction;

    private Boolean isPrivate;

    private List<String> imageUrls;

    public static List<Map<String, Integer>> convertDifficulties(List<Difficulties> difficulties) {
        return difficulties.stream()
                .map(difficulty -> Map.of(difficulty.getDifficulty().getKey(), difficulty.getValue()))
                .collect(Collectors.toList());
    }
}