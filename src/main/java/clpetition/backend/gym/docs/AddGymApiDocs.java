package clpetition.backend.gym.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.gym.dto.request.AddGymRequest;
import clpetition.backend.gym.dto.response.GetGymIdResponse;
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
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Gym API", description = "암장 API")
public interface AddGymApiDocs {

    @Operation(summary = "암장 추가", description = "암장을 추가합니다.")
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
                                                    "gymId": 11,
                                                }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409", description = "❌ 이미 같은 이름이나 주소가 존재하는 암장을 등록 시도",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "code": "GYM_002",
                                                "message": "이미 존재하는 암장입니다.",
                                                "result": null
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<BaseResponse<GetGymIdResponse>> addGym(
            @Parameter(hidden = true)
            @FindMember Member member,

            @RequestBody AddGymRequest addGymRequest
    );
}
