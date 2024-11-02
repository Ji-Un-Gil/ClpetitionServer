package clpetition.backend.instagram.dto.response;

import clpetition.backend.instagram.docs.dto.GetInstagramMediaResponseSchema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetInstagramMediaResponse(
        List<String> mediaUrlList
) implements GetInstagramMediaResponseSchema {
}
