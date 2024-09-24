package clpetition.backend.appVersion.docs;

import clpetition.backend.appVersion.annotation.VersionPattern;
import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "App Version API", description = "iOS 앱 버전 API")
public interface AddAppVersionApiDocs {

    @Operation(summary = "앱 버전 최신화", description = "앱 버전을 저장합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", description = "🟢 정상",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "code": "CREATED",
                                                        "message": "요청에 성공했으며 리소스가 정상적으로 생성되었습니다.",
                                                        "result": null
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<Void>> addAppVersion(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "버전", example = "1.1.0")
            @RequestParam(value = "version") @VersionPattern String version
    );
}
