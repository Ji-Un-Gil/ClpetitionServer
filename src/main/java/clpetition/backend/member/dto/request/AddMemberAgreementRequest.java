package clpetition.backend.member.dto.request;

import clpetition.backend.member.docs.dto.request.AddMemberAgreementRequestSchema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddMemberAgreementRequest(
        @NotNull(message = "마케팅 수신 동의 여부는 필수입니다")
        Boolean marketingAgree,
        @NotNull(message = "푸시알림 수신 동의 여부는 필수입니다")
        Boolean pushAgree
) implements AddMemberAgreementRequestSchema {
}
