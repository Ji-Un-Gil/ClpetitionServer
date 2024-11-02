package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Tag(name = "Instagram API", description = "인스타그램 API")
public interface AuthInstagramApiDocs {

    @Operation(summary = "인스타그램 로그인 API", description = "인스타그램 로그인 및 access token을 저장합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "🟢 정상"),
            }
    )
    @GetMapping("/instagram")
    void authInstagram(
            @Parameter(hidden = true)
            @FindMember Member member,

            @Parameter(hidden = true)
            HttpServletResponse response
    ) throws IOException;
}
