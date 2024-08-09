package clpetition.backend.record.repository;

import clpetition.backend.member.domain.Member;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.dto.response.GetRecordStatisticsPerMonthResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static clpetition.backend.gym.domain.QGym.gym;
import static clpetition.backend.record.domain.QDifficulties.difficulties;
import static clpetition.backend.record.domain.QRecord.record;

@Transactional
@RequiredArgsConstructor
public class RecordQueryRepositoryImpl implements RecordQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GetRecordStatisticsPerMonthResponse findRecordStatisticsPerMonth(Member member, YearMonth yearMonth) {
        List<Tuple> results = jpaQueryFactory
                .select(
                        record.count(),
                        record.exerciseTime,
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
                .fetch();

        Double totalHours = results.stream()
                .map(result -> {
                    LocalTime exerciseTime = result.get(record.exerciseTime);
                    if (exerciseTime == null)
                        return 0L;
                    return Duration.between(LocalTime.MIN, exerciseTime).getSeconds();
                })
                .mapToLong(Long::longValue)
                .sum()
                / 3600.0;

        Tuple result = results.get(0);

        return GetRecordStatisticsPerMonthResponse.builder()
                .totalDay(Optional.ofNullable(result.get(record.count())).orElse(0L).intValue())
                .totalHour(totalHours)
                .totalSend(Optional.ofNullable(result.get(difficulties.value.sum())).orElse(0))
                .totalGym(Optional.ofNullable(result.get(record.gym.countDistinct())).orElse(0L).intValue())
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
                .fetch();
    }
}
