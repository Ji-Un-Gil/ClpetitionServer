package clpetition.backend.gym.dto.response;

import lombok.Builder;

@Builder
public record GetGymDetailsResponse(
        Long id,
        String brand,
        String branch
) {
}
