package clpetition.backend.record.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.dto.response.GetRecordDetailsResponse;
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
public interface GetRecordDetailsApiDocs {

    @Operation(summary = "등반 기록 상세조회", description = "단일 등반 기록의 세부 정보를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
                    @ApiResponse(
                            responseCode = "404", description = "❌ 입력받은 등반 기록 ID가 DB에 존재하지 않음",
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
            }
    )
    ResponseEntity<BaseResponse<GetRecordDetailsResponse>> getRecordDetails(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "등반 기록 ID", example = "2")
            @PathVariable("recordId") Long recordId
    );
}
