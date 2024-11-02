package clpetition.backend.member.docs;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface HandleInstagramCallbackApiDocs {

    @Hidden
    ResponseEntity<BaseResponse<Void>> handleInstagramCallback(
            @FindMember Member member,
            @RequestParam String code
    );
}
