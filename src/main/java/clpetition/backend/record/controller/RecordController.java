package clpetition.backend.record.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.controller.dto.request.AddRecordRequest;
import clpetition.backend.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> addRecord(
            @FindMember Member member,
            @Valid @RequestPart("dto") AddRecordRequest addRecordRequest,
            @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList
            ) {
        return BaseResponse.toResponseEntityContainsStatusAndResult(BaseResponseStatus.CREATED, recordService.addRecord(member, addRecordRequest));
    }
}
