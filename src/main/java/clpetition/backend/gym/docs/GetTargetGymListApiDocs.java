package clpetition.backend.gym.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.gym.dto.response.GetTargetGymListResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Gym API", description = "암장 API")
public interface GetTargetGymListApiDocs {

    @Operation(summary = "암장 검색으로 조회", description = "검색어를 포함하는 암장명을 리스트로 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    ResponseEntity<BaseResponse<List<GetTargetGymListResponse>>> getTargetGymList(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(description = "암장명 검색어", example = "더클라임", in = ParameterIn.QUERY)
            @RequestParam(value = "gymName", required = false, defaultValue = "") String gymName
    );
}
