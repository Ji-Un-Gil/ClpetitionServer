package clpetition.backend.record.repository;

import clpetition.backend.gym.domain.Gym;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.dto.response.GetMainHistoryResponse;
import clpetition.backend.record.dto.response.GetMainStatisticsResponse;
import clpetition.backend.record.dto.response.GetRecordStatisticsPerMonthResponse;
import clpetition.backend.record.dto.response.GetRelatedRecordResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static clpetition.backend.gym.domain.QGym.gym;
import static clpetition.backend.record.domain.QDifficulties.difficulties;
import static clpetition.backend.record.domain.QRecord.record;

@Transactional
@RequiredArgsConstructor
public class RecordQueryRepositoryImpl implements RecordQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private static final Integer RELATED_RECORD_SIZE = 9;
    private static final Integer PAGE_SIZE = 10;

    @Override
    public GetRecordStatisticsPerMonthResponse findRecordStatisticsPerMonth(Member member, YearMonth yearMonth) {
        Tuple result = jpaQueryFactory
                .select(
                        record.date.countDistinct(),
                        difficulties.value.sum(),
                        record.gym.countDistinct()
                )
                .from(record)
                .leftJoin(record.difficulties, difficulties)
                .leftJoin(record.gym, gym)
                .where(
                        record.member.eq(member),
                        record.date.year().eq(yearMonth.getYear()),
                        record.date.month().eq(yearMonth.getMonthValue())
                )
                .fetchOne();

        List<Record> records = jpaQueryFactory
                .selectFrom(record)
                .where(
                        record.member.eq(member),
                        record.date.year().eq(yearMonth.getYear()),
                        record.date.month().eq(yearMonth.getMonthValue())
                )
                .fetch();

        Double totalHours = records.stream()
                .map(record -> Duration.between(LocalTime.MIN, record.getExerciseTime()).getSeconds())
                .mapToLong(Long::longValue)
                .sum() / 3600.0;

        return GetRecordStatisticsPerMonthResponse.builder()
                .totalDay(Optional.ofNullable(result.get(record.date.countDistinct())).orElse(0L).intValue())
                .totalHour(totalHours)
                .totalSend(Optional.ofNullable(result.get(difficulties.value.sum())).orElse(0))
                .totalGym(Optional.ofNullable(result.get(record.gym.countDistinct())).orElse(0L).intValue())
                .build();
    }

    @Override
    public GetMainStatisticsResponse findMainStatistics(Member member, YearMonth yearMonth) {
        Tuple result = jpaQueryFactory
                .select(
                        record.countDistinct(),
                        difficulties.value.sum()
                )
                .from(record)
                .innerJoin(record.difficulties, difficulties)
                .innerJoin(record.gym, gym)
                .where(
                        record.member.eq(member),
                        record.date.year().eq(yearMonth.getYear()),
                        record.date.month().eq(yearMonth.getMonthValue())
                )
                .fetchOne();

        Integer totalSend = jpaQueryFactory
                .select(difficulties.value.sum())
                .from(record)
                .leftJoin(record.difficulties, difficulties)
                .where(record.member.eq(member))
                .fetchOne();

        return GetMainStatisticsResponse.builder()
                .monthTotalRecord(Optional.ofNullable(result.get(record.countDistinct())).orElse(0L).intValue())
                .monthTotalSend(Optional.ofNullable(result.get(difficulties.value.sum())).orElse(0))
                .totalSend(Optional.ofNullable(totalSend).orElse(0))
                .build();
    }

    @Override
    public List<Record> findRecordsPerMonth(Member member, YearMonth yearMonth) {
        return jpaQueryFactory
                .selectFrom(record)
                .where(
                        record.member.eq(member),
                        record.date.year().eq(yearMonth.getYear()),
                        record.date.month().eq(yearMonth.getMonthValue())
                )
                .orderBy(record.date.asc())
                .fetch();
    }

    @Override
    public List<GetRelatedRecordResponse> findRelatedRecord(Gym gym) {
        List<Record> records = jpaQueryFactory
                .selectFrom(record)
                .where(
                        record.gym.eq(gym),
                        record.images.isNotEmpty(),
                        record.isPrivate.eq(false)
                )
                .orderBy(record.date.desc())
                .limit(RELATED_RECORD_SIZE)
                .fetch();

        return records.stream()
                .map(record -> {
                    List<String> images = Record.convertToImageUrls(record.getImages());
                    String thumbnail = images.get(0);
                    return GetRelatedRecordResponse.builder()
                            .recordId(record.getId())
                            .thumbnail(thumbnail)
                            .build();
                })
                .toList();
    }

    @Override
    public GetMainHistoryResponse findMainHistory(Member member, YearMonth yearMonth) {
        List<LocalDate> recordDates = jpaQueryFactory
                .select(record.date)
                .from(record)
                .where(
                        record.member.eq(member),
                        record.date.year().eq(yearMonth.getYear()),
                        record.date.month().eq(yearMonth.getMonthValue())
                )
                .distinct()
                .orderBy(record.date.asc())
                .fetch();

        return GetMainHistoryResponse.builder()
                .totalDay(recordDates.size())
                .recordDates(recordDates)
                .build();
    }

    @Override
    public Map<String, Object> findRecordHistory(Member member, Long lastRecordId, boolean isMyself) {
        LocalDate lastRecordDate = jpaQueryFactory
                .select(record.date)
                .from(record)
                .where(
                        record.member.eq(member),
                        lastRecordId != null ? record.id.eq(lastRecordId) : record.date.eq(
                                        JPAExpressions
                                                .select(record.date.max())
                                                .from(record)
                                                .where(record.member.eq(member))
                        )
                )
                .fetchOne();

        if (lastRecordDate == null)
            return Map.of("hasNext", false, "recordHistory", new ArrayList<>());

        List<Record> results = jpaQueryFactory
                .selectFrom(record)
                .where(
                        record.member.eq(member),
                        lastRecordId != null ?
                                (record.date.lt(lastRecordDate)
                                        .or(record.date.eq(lastRecordDate).and(record.id.lt(lastRecordId)))) : null,
                        isMyself ? null : record.isPrivate.eq(false)
                )
                .orderBy(record.date.desc(), record.id.desc())
                .limit(PAGE_SIZE + 1)
                .fetch();

        boolean hasNext = results.size() > PAGE_SIZE;
        if (hasNext)
            results = results.subList(0, PAGE_SIZE);

        return Map.of("hasNext", hasNext, "recordHistory", results);
    }
}
