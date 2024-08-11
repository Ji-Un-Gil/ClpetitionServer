package clpetition.backend.record.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.annotation.LocalDatePattern;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.docs.*;
import clpetition.backend.record.dto.request.AddRecordRequest;
import clpetition.backend.record.dto.response.GetRecordDetailsResponse;
import clpetition.backend.record.dto.response.GetRecordIdResponse;
import clpetition.backend.record.dto.response.GetRecordStatisticsPerMonthResponse;
import clpetition.backend.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
        GetRecordDetailsPerMonthApiDocs {

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
                (recordService.getRecordDetails(member, recordId));
    }

    @PutMapping(value = "/{recordId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<GetRecordIdResponse>> changeRecord(
            @FindMember Member member,
            @PathVariable("recordId") Long recordId,
            @Valid @RequestPart("dto") AddRecordRequest addRecordRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList
    ) {
        return BaseResponse.toResponseEntityContainsStatusAndResult
                (BaseResponseStatus.CREATED, recordService.changeRecord(member, recordId, addRecordRequest, multipartFileList));
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
    public ResponseEntity<BaseResponse<Map<LocalDate, List<GetRecordDetailsResponse>>>> getRecordDetailsPerMonth(
            @FindMember Member member,
            @LocalDatePattern(pattern = "yyyy-M") @PathVariable("yearMonth") String yearMonth
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getRecordDetailsPerMonth(member, YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-M"))));
    }
}
