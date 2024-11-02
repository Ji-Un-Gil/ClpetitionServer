package clpetition.backend.appVersion.controller;

import clpetition.backend.appVersion.annotation.VersionPattern;
import clpetition.backend.appVersion.docs.AddAppVersionApiDocs;
import clpetition.backend.appVersion.docs.GetLatestAppVersionApiDocs;
import clpetition.backend.appVersion.dto.response.GetLatestAppVersionResponse;
import clpetition.backend.appVersion.service.AppVersionService;
import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app_version")
public class AppVersionController implements
        AddAppVersionApiDocs,
        GetLatestAppVersionApiDocs {

    private final AppVersionService appVersionService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<Void>> addAppVersion(
            @FindMember Member member,
            @RequestParam(value = "type") String appType,
            @RequestParam(value = "version") @VersionPattern String version
    ) {
        appVersionService.addAppVersion(appType, version);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<GetLatestAppVersionResponse>> getLatestAppVersion(
            @FindMember Member member,
            @RequestParam(value = "type") String appType
    ) {
        return BaseResponse.toResponseEntityContainsResult(appVersionService.getLatestAppVersion(appType));
    }
}
