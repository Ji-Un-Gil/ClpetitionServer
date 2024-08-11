package clpetition.backend.record.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.docs.dto.request.AddRecordRequestSchema;
import clpetition.backend.record.dto.request.AddRecordRequest;
import clpetition.backend.record.dto.response.GetRecordIdResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Record API", description = "등반 기록 API")
public interface ChangeRecordApiDocs {

    @Operation(summary = "등반 기록 수정", description = "등반 기록을 수정합니다.(삭제 후 재등록)")
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
                                                "result": {
                                                    "id": 0
                                                }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description =
                                    """
                                    ❌ 1) 입력받은 등반 기록 ID가 DB에 존재하지 않거나 현재 사용자의 등반 기록이 아님 <br/>
                                    ❌ 2) DB에 등록되지 않은 암장 저장 시도
                                    """,
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "❌ 1) 입력받은 등반 기록 ID가 DB에 존재하지 않거나, 현재 사용자의 등반 기록이 아님",
                                                    value = """
                                                            {
                                                                "code": "RECORD_001",
                                                                "message": "존재하지 않는 기록입니다.",
                                                                "result": null
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "❌ 2) DB에 등록되지 않은 암장 저장 시도",
                                                    value = """
                                                            {
                                                                "code": "GYM_001",
                                                                "message": "존재하지 않는 암장입니다.",
                                                                "result": null
                                                            }
                                                            """
                                            )
                                    }
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
    ResponseEntity<BaseResponse<GetRecordIdResponse>> changeRecord(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "등반 기록 ID", example = "2")
            @PathVariable("recordId") Long recordId,

            @Parameter(
                    description = "DTO",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddRecordRequestSchema.class)
                    )
            )
            @Valid @RequestPart("dto") AddRecordRequest addRecordRequest,

            @Parameter(
                    description = "첨부 사진",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                    )
            )
            @RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList
    );
}
