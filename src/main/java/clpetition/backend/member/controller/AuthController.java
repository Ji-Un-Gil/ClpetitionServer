package clpetition.backend.member.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.dto.request.SocialLoginRequest;
import clpetition.backend.member.dto.response.SocialLoginResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

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

    @GetMapping("/duplicated")
    public ResponseEntity<BaseResponse<Void>> checkNickname(
            @FindMember Member member,
            @RequestParam("nickname")
            @NotBlank(message = "닉네임을 입력해주세요")
            @Length(max = 10, message = "닉네임은 10글자 이하로 작성해주세요")
            String nickname
    ) {
        if (authService.checkNickname(member, nickname))
            throw new BaseException(BaseResponseStatus.DUPLICATED_NICKNAME);
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
}
