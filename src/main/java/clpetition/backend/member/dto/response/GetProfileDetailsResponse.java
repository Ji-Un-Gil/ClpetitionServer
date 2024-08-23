package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.GetProfileDetailsResponseSchema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetProfileDetailsResponse(
        String nickname,
        String mainGymName,
        LocalDate startDate,
        LocalDate birthDate,
        String gender,
        Integer height,
        Integer reach,
        String instagram,
        String inviteCode,
        String profileImage
) implements GetProfileDetailsResponseSchema {
}
