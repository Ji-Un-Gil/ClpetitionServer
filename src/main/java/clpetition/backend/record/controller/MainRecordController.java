package clpetition.backend.record.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.annotation.LocalDatePattern;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.docs.GetMainHistoryApiDocs;
import clpetition.backend.record.docs.GetMainStatisticsApiDocs;
import clpetition.backend.record.dto.response.GetMainHistoryResponse;
import clpetition.backend.record.dto.response.GetMainStatisticsResponse;
import clpetition.backend.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainRecordController implements
        GetMainStatisticsApiDocs,
        GetMainHistoryApiDocs {

    private final RecordService recordService;

    @GetMapping("/statistics/{yearMonth}")
    public ResponseEntity<BaseResponse<GetMainStatisticsResponse>> getMainStatistics(
            @FindMember Member member,
            @LocalDatePattern(pattern = "yyyy-M") @PathVariable("yearMonth") String yearMonth
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getMainStatistics(member, YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-M"))));
    }

    @GetMapping("/history/{yearMonth}")
    public ResponseEntity<BaseResponse<GetMainHistoryResponse>> getMainHistory(
            @FindMember Member member,
            @LocalDatePattern(pattern = "yyyy-M") @PathVariable("yearMonth") String yearMonth
    ) {
        return BaseResponse.toResponseEntityContainsResult
                (recordService.getMainHistory(member, YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-M"))));
    }
}
