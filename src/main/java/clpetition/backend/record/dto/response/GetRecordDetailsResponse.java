package clpetition.backend.record.dto.response;

import clpetition.backend.gym.dto.response.GetGymDetailsResponse;
import clpetition.backend.record.docs.dto.response.GetRecordDetailsResponseSchema;
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
public class GetRecordDetailsResponse implements GetRecordDetailsResponseSchema {
    private Long recordId;

    private GetGymDetailsResponse gym;

    private LocalDate date;

    private Integer weekday;

    private Map<String, Integer> difficulties;

    private String memo;

    private LocalTime exerciseTime;

    private Integer satisfaction;

    private Boolean isPrivate;

    private List<String> imageUrls;
}
