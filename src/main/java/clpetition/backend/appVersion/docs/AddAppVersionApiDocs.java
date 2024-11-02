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

@Tag(name = "App Version API", description = "앱 버전 API")
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
                    @ApiResponse(
                            responseCode = "404", description = "❌ 앱 타입(iOS, AOS)이 존재하지 않음",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "APP_VERSION_001",
                                                "message": "존재하지 않는 애플리케이션 타입입니다.",
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

            @Parameter(description = "앱 타입", example = "iOS")
            @RequestParam(value = "type") String appType,

            @Parameter(description = "버전", example = "1.1.0")
            @RequestParam(value = "version") @VersionPattern String version
    );
}
