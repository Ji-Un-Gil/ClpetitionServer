package clpetition.backend.gym.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.gym.docs.GetTargetGymListApiDocs;
import clpetition.backend.gym.dto.response.GetTargetGymListResponse;
import clpetition.backend.gym.service.GymService;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController implements
        GetTargetGymListApiDocs {

    private final GymService gymService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetTargetGymListResponse>>> getTargetGymList(
            @FindMember Member member,
            @RequestParam("gymName") String gymName
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (gymService.getTargetGymList(member, gymName));
    }
}
