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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Record API", description = "등반 기록 API")
public interface AddRecordApiDocs {

    @Operation(summary = "등반 기록 저장", description = "등반 기록을 저장합니다.")
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
                                                            "recordId": 0,
                                                            "imageUrls": [
                                                                "url",
                                                                "url2"
                                                            ]
                                                        }
                                                    }
                                                    """
                                    )
                            )
                    ),
                    /*@ApiResponse(
                            responseCode = "400", description = "❌ 미래의 날짜를 등반일로 기록 시도",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "RECORD_002",
                                                "message": "등반일은 미래일 수 없습니다.",
                                                "result": null
                                            }
                                            """
                                    )

                            )
                    ),*/
                    @ApiResponse(
                            responseCode = "404", description = "❌ DB에 등록되지 않은 암장 저장 시도",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "GYM_001",
                                                "message": "존재하지 않는 암장입니다.",
                                                "result": null
                                            }
                                            """
                                    )

                            )
                    ),
                    @ApiResponse(
                            responseCode = "502", description = "❌ S3 사진 등록에서 문제 발생",
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
    ResponseEntity<BaseResponse<GetRecordIdResponse>> addRecord(
            @Parameter(hidden = true)
            @FindMember Member member,

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
