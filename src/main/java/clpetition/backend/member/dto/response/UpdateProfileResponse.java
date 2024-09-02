package clpetition.backend.member.dto.response;

import clpetition.backend.member.docs.dto.response.UpdateProfileResponseSchema;
import lombok.Builder;

@Builder
public record UpdateProfileResponse(
        String profileImageUrl
) implements UpdateProfileResponseSchema {
}
