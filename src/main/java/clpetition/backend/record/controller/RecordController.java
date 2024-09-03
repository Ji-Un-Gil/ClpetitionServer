package clpetition.backend.record.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.annotation.LocalDatePattern;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.docs.*;
import clpetition.backend.record.dto.request.AddRecordRequest;
import clpetition.backend.record.dto.response.*;
import clpetition.backend.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController implements
        AddRecordApiDocs,
        GetRecordDetailsApiDocs,
        ChangeRecordApiDocs,
        DeleteRecordApiDocs,
        GetRecordStatisticsPerMonthApiDocs,
        GetRecordDetailsPerMonthApiDocs,
        GetGymInfoAndRelatedRecordApiDocs,
        GetRecordDetailsAllApiDocs {

    private final RecordService recordService;

    @PostMapping(value = "", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<GetRecordIdResponse>> addRecord(
            @FindMember Member member,
            @Valid @RequestPart("dto") AddRecordRequest addRecordRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList
            ) {
        return BaseResponse.toResponseEntityContainsStatusAndResult
                (BaseResponseStatus.CREATED, recordService.addRecord(member, addRecordRequest, multipartFileList));
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<BaseResponse<GetRecordDetailsResponse>> getRecordDetails(
            @FindMember Member member,
            @PathVariable("recordId") Long recordId
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getRecordDetails(recordId));
    }

    @PutMapping(value = "/{recordId}")
    public ResponseEntity<BaseResponse<GetRecordIdResponse>> changeRecord(
            @FindMember Member member,
            @PathVariable("recordId") Long recordId,
            @Valid @RequestBody AddRecordRequest addRecordRequest
    ) {
        return BaseResponse.toResponseEntityContainsStatusAndResult
                (BaseResponseStatus.CREATED, recordService.changeRecord(member, recordId, addRecordRequest));
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<BaseResponse<Void>> deleteRecord(
            @FindMember Member member,
            @PathVariable("recordId") Long recordId
    ) {
        recordService.deleteRecord(member, recordId);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.DELETED);
    }

    @GetMapping("/statistics/{yearMonth}")
    public ResponseEntity<BaseResponse<GetRecordStatisticsPerMonthResponse>> getRecordStatisticsPerMonth(
            @FindMember Member member,
            @LocalDatePattern(pattern = "yyyy-M") @PathVariable("yearMonth") String yearMonth
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getRecordStatisticsPerMonth(member, YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-M"))));
    }

    @GetMapping("/month/{yearMonth}")
    public ResponseEntity<BaseResponse<List<GetRecordDetailsResponse>>> getRecordDetailsPerMonth(
            @FindMember Member member,
            @LocalDatePattern(pattern = "yyyy-M") @PathVariable("yearMonth") String yearMonth
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getRecordDetailsPerMonth(member, YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-M"))));
    }

    @GetMapping("/related/{gymId}")
    public ResponseEntity<BaseResponse<GetGymInfoAndRelatedRecordResponse>> getGymInfoAndRelatedRecord(
            @FindMember Member member,
            @PathVariable("gymId") Long gymId
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getGymInfoAndRelatedRecord(member, gymId));
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<List<GetRecordDetailsResponse>>> getRecordDetailsAll(
            @FindMember Member member
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getRecordDetailsAll(member));
    }
}
