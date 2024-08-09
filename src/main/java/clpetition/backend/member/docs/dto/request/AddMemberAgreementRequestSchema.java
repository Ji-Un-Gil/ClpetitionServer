package clpetition.backend.member.docs.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface AddMemberAgreementRequestSchema {

    @Schema(description = "마케팅 수신 동의 여부", example = "true")
    Boolean marketingAgree();

    @Schema(description = "푸시알림 수신 동의 여부", example = "false")
    Boolean pushAgree();
}
