package clpetition.backend.record.dto.response;

import clpetition.backend.gym.dto.response.GetGymDetailsResponse;
import clpetition.backend.record.docs.dto.response.GetRecordDetailsResponseSchema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Builder
public record GetRecordDetailsResponse(
        Long recordId,
        GetGymDetailsResponse gym,
        LocalDate date,
        Integer weekday,
        Map<String, Integer> difficulties,
        String memo,
        LocalTime exerciseTime,
        Integer satisfaction,
        Boolean isPrivate,
        List<String> imageUrls,
        Long memberId,
        String profileImageUrl,
        String nickname,
        String difficulty,
        Integer rank
) implements GetRecordDetailsResponseSchema {
}
