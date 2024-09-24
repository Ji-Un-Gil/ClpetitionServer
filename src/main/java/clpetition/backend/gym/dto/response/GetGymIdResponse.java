package clpetition.backend.gym.dto.response;

import clpetition.backend.gym.docs.dto.response.GetGymIdResponseSchema;
import lombok.Builder;

@Builder
public record GetGymIdResponse(
        Long gymId
) implements GetGymIdResponseSchema {
}
