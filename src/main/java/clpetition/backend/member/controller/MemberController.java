package clpetition.backend.member.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.annotation.NicknamePattern;
import clpetition.backend.member.docs.*;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.request.UpdateProfileRequest;
import clpetition.backend.member.dto.response.GetProfileDetailsResponse;
import clpetition.backend.member.dto.response.GetProfileResponse;
import clpetition.backend.member.dto.response.GetRecordHistoryPageResponse;
import clpetition.backend.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController implements
        CheckNicknameApiDocs,
        GetProfileApiDocs,
        GetProfileDetailsApiDocs,
        UpdateProfileApiDocs,
        GetRecordHistoryApiDocs {

    private final MemberService memberService;

    @GetMapping("/validation")
    public ResponseEntity<BaseResponse<Void>> checkNickname(
            @FindMember Member member,
            @RequestParam("nickname")
            @NotBlank(message = "닉네임을 입력해주세요")
            @Length(max = 10, message = "닉네임은 10글자 이하로 작성해주세요")
            @NicknamePattern
            String nickname
    ) {
        if (memberService.checkNickname(member, nickname))
            throw new BaseException(BaseResponseStatus.DUPLICATED_NICKNAME);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/profile")
    public ResponseEntity<BaseResponse<GetProfileResponse>> getProfile(
            @FindMember Member member
    ) {
        return BaseResponse.toResponseEntityContainsResult(memberService.getProfile(member));
    }

    @GetMapping("/profile/details")
    public ResponseEntity<BaseResponse<GetProfileDetailsResponse>> getProfileDetails(
            @FindMember Member member
    ) {
        return BaseResponse.toResponseEntityContainsResult(memberService.getProfileDetails(member));
    }

    @PatchMapping(value = "/profile", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Void>> updateProfile(
            @FindMember Member member,
            @Valid @RequestPart("dto") UpdateProfileRequest updateProfileRequest,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile
            ) throws IOException {
        memberService.updateProfile(member, updateProfileRequest, multipartFile);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/history")
    public ResponseEntity<BaseResponse<GetRecordHistoryPageResponse>> getRecordHistory(
            @FindMember Member member,
            @RequestParam(value = "lastRecordId", required = false) Long lastRecordId
    ) {
        return BaseResponse.toResponseEntityContainsResult(memberService.getRecordHistory(member, lastRecordId));
    }
}
