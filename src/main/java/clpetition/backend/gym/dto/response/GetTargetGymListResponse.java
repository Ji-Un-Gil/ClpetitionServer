package clpetition.backend.gym.dto.response;

import clpetition.backend.gym.docs.dto.response.GetTargetGymListResponseSchema;
import lombok.Builder;

@Builder
public record GetTargetGymListResponse(
        Long gymId,
        String gymName,
        String address,
        Boolean isVisited
) implements GetTargetGymListResponseSchema {
}
