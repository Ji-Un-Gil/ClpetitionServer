package clpetition.backend.gym.dto.request;

import clpetition.backend.gym.docs.dto.request.AddGymRequestSchema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddGymRequest(
        @NotBlank(message = "암장명은 필수입니다")
        String name,
        @NotBlank(message = "지역은 필수입니다")
        String region,
        @NotBlank(message = "주소는 필수입니다")
        String address,
        @NotBlank(message = "암장 줄임말은 필수입니다")
        String shortName
) implements AddGymRequestSchema {
}
