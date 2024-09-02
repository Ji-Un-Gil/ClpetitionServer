package clpetition.backend.member.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.docs.AddMemberAgreementApiDocs;
import clpetition.backend.member.docs.DeleteMemberApiDocs;
import clpetition.backend.member.docs.LogoutApiDocs;
import clpetition.backend.member.docs.SocialLoginApiDocs;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.request.AddMemberAgreementRequest;
import clpetition.backend.member.dto.request.SocialLoginRequest;
import clpetition.backend.member.dto.response.SocialLoginResponse;
import clpetition.backend.member.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements
        SocialLoginApiDocs,
        AddMemberAgreementApiDocs,
        LogoutApiDocs,
        DeleteMemberApiDocs {

    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<SocialLoginResponse>> socialLogin(
            @Valid @RequestBody SocialLoginRequest socialLoginRequest
    ) {
        SocialLoginResponse socialLoginResponse = authService.socialLogin(socialLoginRequest);
        if (socialLoginResponse.role() == null)
            return BaseResponse.toResponseEntityContainsStatusAndResult(BaseResponseStatus.CREATED, socialLoginResponse);
        return BaseResponse.toResponseEntityContainsResult(socialLoginResponse);
    }

    @PostMapping("/agreement")
    public ResponseEntity<BaseResponse<Void>> addMemberAgreement(
            @FindMember Member member,
            @Valid @RequestBody AddMemberAgreementRequest addMemberAgreementRequest
    ) {
        authService.addMemberAgreement(member, addMemberAgreementRequest);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@FindMember Member member
    ) {
        authService.logout(member);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/fcm")
    public ResponseEntity<BaseResponse<Void>> addFcmToken(
            @FindMember Member member,
            @RequestParam("token")
            @NotBlank(message = "fcm token을 입력해주세요")
            String fcmToken
    ) {
        authService.addFcmToken(member, fcmToken);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<BaseResponse<Void>> deleteMember(
            @FindMember Member member
    ) {
        authService.deleteMember(member);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.DELETED);
    }
}
