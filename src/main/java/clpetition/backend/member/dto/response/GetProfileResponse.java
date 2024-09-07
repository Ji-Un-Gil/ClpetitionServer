package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.GetProfileResponseSchema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetProfileResponse(
        String nickname,
        Long mainGymId,
        LocalDate startDate,
        Integer height,
        Integer reach,
        String instagram,
        String profileImageUrl,
        Long followerCount,
        Long followingCount,
        Long totalRecord,
        LocalDate birthDate,
        String gender,
        String inviteCode,
        String difficulty,
        Integer rank
) implements GetProfileResponseSchema {
}
