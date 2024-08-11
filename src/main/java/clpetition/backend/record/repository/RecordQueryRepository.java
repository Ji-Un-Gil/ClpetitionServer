package clpetition.backend.record.repository;

import clpetition.backend.member.domain.Member;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.dto.response.GetRecordStatisticsPerMonthResponse;

import java.time.YearMonth;
import java.util.List;

public interface RecordQueryRepository {
    GetRecordStatisticsPerMonthResponse findRecordStatisticsPerMonth(Member member, YearMonth yearMonth);
    List<Record> findRecordsPerMonth(Member member, YearMonth yearMonth);
}
