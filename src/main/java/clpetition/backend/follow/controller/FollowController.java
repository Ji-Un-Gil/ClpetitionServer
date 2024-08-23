package clpetition.backend.follow.controller;

import clpetition.backend.follow.docs.AddFollowApiDocs;
import clpetition.backend.follow.docs.DeleteFollowApiDocs;
import clpetition.backend.follow.service.FollowService;
import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController implements
        AddFollowApiDocs,
        DeleteFollowApiDocs {

    private final FollowService followService;

    @PostMapping("/{followerId}")
    public ResponseEntity<BaseResponse<Void>> addFollow(
            @FindMember Member member,
            @PathVariable("followerId") Long followerId
    ) {
        followService.addFollow(member, followerId);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @DeleteMapping("/{followerId}")
    public ResponseEntity<BaseResponse<Void>> deleteFollow(
            @FindMember Member member,
            @PathVariable("followerId") Long followerId
    ) {
        followService.deleteFollow(member, followerId);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.DELETED);
    }
}
