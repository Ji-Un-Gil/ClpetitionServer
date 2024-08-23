package clpetition.backend.member.dto.request;

import clpetition.backend.global.annotation.LocalDatePattern;
import clpetition.backend.member.docs.dto.request.UpdateProfileRequestSchema;
import lombok.Builder;

@Builder
public record UpdateProfileRequest(
        String nickname,
        Long mainGymId,
        @LocalDatePattern
        String startDate,
        @LocalDatePattern
        String birthDate,
        String gender,
        Integer height,
        Integer reach,
        String instagram
) implements UpdateProfileRequestSchema {
}