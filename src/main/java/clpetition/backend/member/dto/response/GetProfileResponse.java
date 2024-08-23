package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.GetProfileResponseSchema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetProfileResponse(
        String nickname,
        String profileImage,
        Long followerCount,
        Long followingCount,
        String mainGymName,
        Integer height,
        Integer reach,
        Long totalRecord,
        LocalDate startDate
) implements GetProfileResponseSchema {
}
