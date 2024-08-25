package clpetition.backend.gym.dto.response;

import clpetition.backend.gym.docs.dto.response.GetGymDetailsResponseSchema;
import lombok.Builder;

@Builder
public record GetGymDetailsResponse(
        Long gymId,
        String gymName,
        String shortName
) implements GetGymDetailsResponseSchema {
}
