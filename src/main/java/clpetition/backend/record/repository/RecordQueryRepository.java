package clpetition.backend.record.repository;

import clpetition.backend.gym.domain.Gym;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.response.GetRecordHistoryPageResponse;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.dto.response.GetMainHistoryResponse;
import clpetition.backend.record.dto.response.GetMainStatisticsResponse;
import clpetition.backend.record.dto.response.GetRecordStatisticsPerMonthResponse;
import clpetition.backend.record.dto.response.GetRelatedRecordResponse;

import java.time.YearMonth;
import java.util.List;

public interface RecordQueryRepository {
    GetRecordStatisticsPerMonthResponse findRecordStatisticsPerMonth(Member member, YearMonth yearMonth);
    GetMainStatisticsResponse findMainStatistics(Member member, YearMonth yearMonth);
    List<Record> findRecordsPerMonth(Member member, YearMonth yearMonth);
    List<GetRelatedRecordResponse> findRelatedRecord(Gym gym);
    GetMainHistoryResponse findMainHistory(Member member, YearMonth yearMonth);
    GetRecordHistoryPageResponse findRecordHistory(Member member, Long lastRecordId, boolean isMyself);
}
