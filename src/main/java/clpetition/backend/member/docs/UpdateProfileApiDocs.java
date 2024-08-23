package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.docs.dto.request.UpdateProfileRequestSchema;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.request.UpdateProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Member API", description = "사용자 API")
public interface UpdateProfileApiDocs {

    @Operation(summary = "프로필 수정 API", description = "프로필을 수정합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "404", description = "❌ 프로필이 존재하지 않음",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "PROFILE_001",
                                                "message": "존재하지 않는 프로필입니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "502", description = "❌ S3 사진 등록 또는 삭제에서 문제 발생",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "FILE_002",
                                                "message": "파일 서버의 문제로 작업에 실패했습니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    ),
            }
    )
    ResponseEntity<BaseResponse<Void>> updateProfile(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(
                    description = "DTO",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateProfileRequestSchema.class)
                    )
            )
            @Valid @RequestPart("dto") UpdateProfileRequest updateProfileRequest,

            @Parameter(
                    description = "첨부 사진",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                    )
            )
            @RequestPart(value = "image", required = false) MultipartFile multipartFile
    ) throws IOException;
}
