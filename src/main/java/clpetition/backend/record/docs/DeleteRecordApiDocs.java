package clpetition.backend.record.docs;

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
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Record API", description = "등반 기록 API")
public interface DeleteRecordApiDocs {

    @Operation(summary = "등반 기록 삭제", description = "등반 기록을 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "404", description = "❌ 입력받은 등반 기록 ID가 DB에 존재하지 않거나, 현재 사용자의 등반 기록이 아님",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "RECORD_001",
                                                "message": "존재하지 않는 기록입니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "502", description = "❌ S3 사진 삭제에서 문제 발생",
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
    ResponseEntity<BaseResponse<Void>> deleteRecord(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "등반 기록 ID", example = "2")
            @PathVariable("recordId") Long recordId
    );
}
