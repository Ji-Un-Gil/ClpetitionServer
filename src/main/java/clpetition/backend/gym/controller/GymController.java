package clpetition.backend.gym.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.docs.AddGymApiDocs;
import clpetition.backend.gym.docs.GetTargetGymListApiDocs;
import clpetition.backend.gym.dto.request.AddGymRequest;
import clpetition.backend.gym.dto.response.GetGymIdResponse;
import clpetition.backend.gym.dto.response.GetTargetGymListResponse;
import clpetition.backend.gym.service.GymService;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController implements
        GetTargetGymListApiDocs,
        AddGymApiDocs {

    private final GymService gymService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<GetTargetGymListResponse>>> getTargetGymList(
            @FindMember Member member,
            @RequestParam(value = "gymName", required = false, defaultValue = "") String gymName
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (gymService.getTargetGymList(member, gymName));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<GetGymIdResponse>> addGym(
            @FindMember Member member,
            @RequestBody AddGymRequest addGymRequest
    ) {
        return BaseResponse.toResponseEntityContainsStatusAndResult(
                BaseResponseStatus.CREATED, gymService.addGym(addGymRequest));
    }
}
