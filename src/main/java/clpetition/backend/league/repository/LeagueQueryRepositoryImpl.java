package clpetition.backend.league.repository;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.league.dto.response.GetLeagueRankMemberResponse;
import clpetition.backend.league.dto.response.GetLeagueRankResponse;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static clpetition.backend.league.domain.QLeague.league;
import static clpetition.backend.member.domain.QMember.member;
import static clpetition.backend.record.domain.QDifficulties.difficulties;
import static clpetition.backend.record.domain.QRecord.record;
import static com.querydsl.core.types.dsl.Expressions.numberTemplate;

@Transactional
@RequiredArgsConstructor
public class LeagueQueryRepositoryImpl implements LeagueQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final NumberTemplate<Long> exerciseTimeInSeconds = numberTemplate(
            Long.class,
            "COALESCE(SUM(CASE WHEN {0} IS NOT NULL THEN TIME_TO_SEC({1}) END), 0)",
            difficulties.id,
            record.exerciseTime
    );

    private final NumberTemplate<Long> totalDays = numberTemplate(
            Long.class,
            "COUNT(DISTINCT CASE WHEN {0} IS NOT NULL THEN {1} END)",
            difficulties.id,
            record.date
    );

    private final NumberTemplate<Integer> totalValue = numberTemplate(
            Integer.class,
            "COALESCE(SUM({0}), 0)",
            difficulties.value
    );

    @Override
    public List<GetLeagueRankResponse> getLeagueRankTopFifty(Integer season, Difficulty difficulty) {
        List<Tuple> results = fetchRank(season, difficulty);

        // 랭킹 리스트 생성
        List<GetLeagueRankResponse> getLeagueRankResponseList = new ArrayList<>();
        Integer rank = 1;

        for (int i = 0; i < results.size(); i++) {
            if (i >= 50) break;
            Tuple result = results.get(i);

            Long memberId = result.get(member.id);
            String profileImageUrl = result.get(member.profileImage);
            String nickname = result.get(member.nickname);
            Integer totalDay = Optional.ofNullable(result.get(totalDays)).orElse(0L).intValue();
            Integer totalSend = Optional.ofNullable(result.get(totalValue)).orElse(0);
            Long totalSecond = Optional.ofNullable(result.get(exerciseTimeInSeconds)).orElse(0L);
            Integer totalHour = Math.round(totalSecond / 3600f);

            // 동률 처리
            if (i > 0) {
                Tuple previousResult = results.get(i - 1);
                if (Optional.ofNullable(previousResult.get(totalValue)).orElse(0).equals(totalSend) &&
                        Optional.ofNullable(previousResult.get(totalDays)).orElse(0L).equals(totalDay.longValue())) {
                    rank = getLeagueRankResponseList.get(i - 1).rank();
                } else {
                    rank = i + 1;
                }
            }

            getLeagueRankResponseList.add(
                    GetLeagueRankResponse.builder()
                            .rank(rank)
                            .memberId(memberId)
                            .profileImageUrl(profileImageUrl)
                            .nickname(nickname)
                            .totalDay(totalDay)
                            .totalSend(totalSend)
                            .totalHour(totalHour)
                            .build()
            );
        }

        return getLeagueRankResponseList;
    }

    @Override
    public GetLeagueRankMemberResponse getLeagueRankMember(Member member, Integer season, Difficulty difficulty) {
        List<Tuple> results = fetchRank(season, difficulty);

        int targetIndex = -1;
        int highTotalSend = Integer.MAX_VALUE; // 등수를 올릴 수 있는, 나보다 높은 가장 가까운 완등 수
        int nextTotalSend = Integer.MAX_VALUE; // 나 또는 나보다 위에 있지만 값이 같은 완등 수
        int currentTotalSend = 0;
        List<Integer> rankList = new ArrayList<>(); // 순위표
        int rank = 1;

        // member 및 다음으로 높은 완등 수 찾기, 순위 매기기
        for (int i = 0; i < results.size(); i++) {
            Tuple result = results.get(i);

            // 다음으로 높은 완등 수 찾기 위한 작업
            if (targetIndex == -1) {
                currentTotalSend = Optional.ofNullable(result.get(totalValue)).orElse(0);
                if (nextTotalSend > currentTotalSend) {
                    highTotalSend = nextTotalSend;
                    nextTotalSend = currentTotalSend;
                }
            }

            // 순위 매기기 작업
            if (i > 0) {
                Tuple previousResult = results.get(i - 1);
                if (Optional.ofNullable(previousResult.get(totalValue)).orElse(0)
                        .equals(Optional.ofNullable(result.get(totalValue)).orElse(0)) &&
                        Optional.ofNullable(previousResult.get(totalDays)).orElse(0L)
                                .equals(Optional.ofNullable(result.get(totalDays)).orElse(0L))) {
                    rank = rankList.get(i - 1);
                } else {
                    rank = i + 1;
                }
            }
            rankList.add(rank);

            // member 찾기 위한 작업
            if (Objects.equals(result.get(QMember.member.id), member.getId()))
                targetIndex = i;
        }

        if (targetIndex == -1)
            throw new BaseException(BaseResponseStatus.LEAGUE_MEMBER_NOT_FOUND_ERROR);

        // member 앞뒤 +25(예외 포함)가 되도록 범위 지정
        int startIndex, endIndex;
        startIndex = Math.max(0, targetIndex - 25);
        switch (targetIndex) {
            case 0 -> endIndex = Math.min(results.size(), targetIndex + 28);
            case 1 -> endIndex = Math.min(results.size(), targetIndex + 27);
            default -> endIndex = Math.min(results.size(), targetIndex + 26);
        }

        List<GetLeagueRankResponse> getLeagueRankResponseList = new ArrayList<>();

        for (int i = startIndex; i < endIndex; i++) {
            Tuple result = results.get(i);

            Long memberId = result.get(QMember.member.id);
            String profileImageUrl = result.get(QMember.member.profileImage);
            String nickname = result.get(QMember.member.nickname);
            Integer totalDay = Optional.ofNullable(result.get(totalDays)).orElse(0L).intValue();
            Integer totalSend = Optional.ofNullable(result.get(totalValue)).orElse(0);
            Long totalSecond = Optional.ofNullable(result.get(exerciseTimeInSeconds)).orElse(0L);
            Integer totalHour = Math.round(totalSecond / 3600f);

            getLeagueRankResponseList.add(
                    GetLeagueRankResponse.builder()
                            .rank(rankList.get(i))
                            .memberId(memberId)
                            .profileImageUrl(profileImageUrl)
                            .nickname(nickname)
                            .totalDay(totalDay)
                            .totalSend(totalSend)
                            .totalHour(totalHour)
                            .build()
            );
        }

        return GetLeagueRankMemberResponse.builder()
                .getLeagueRankResponseList(getLeagueRankResponseList)
                // 1. highTotalSend 가 Integer.MAX_VALUE 인 경우
                // 1-1. 순위는 1위가 아닌 경우에는(등반일 수 차이로 밀린 경우) highTotalSend 를 1로 설정
                // 1-2. 순위가 1위인 경우 더 이상의 목표가 없으므로, -1로 설정
                // 2. highTotalSend 가 존재하는 경우
                // 2-1. highTotalSend(다음 목표 완등 수) - currentTotalSend(현재 사용자의 완등 수) + 1로 설정
                .targetSend(
                        highTotalSend == Integer.MAX_VALUE ?
                                (rankList.get(targetIndex) > 1 ? 1 : -1) : highTotalSend - currentTotalSend + 1
                )
                .build();
    }

    @Override
    public Integer getLeagueRank(Member member, Integer season, Difficulty difficulty) {
        List<Tuple> results = fetchRank(season, difficulty);

        // 시즌 전체가 존재하지 않는 상황일 때
        if (results.isEmpty())
            return null;

        int targetIndex = -1;
        List<Integer> rankList = new ArrayList<>(); // 순위표
        int rank = 1;

        // member 탐색, 순위 매기기
        for (int i = 0; i < results.size(); i++) {
            Tuple result = results.get(i);

            // 순위 매기기 작업
            if (i > 0) {
                Tuple previousResult = results.get(i - 1);
                if (Optional.ofNullable(previousResult.get(totalValue)).orElse(0)
                        .equals(Optional.ofNullable(result.get(totalValue)).orElse(0)) &&
                        Optional.ofNullable(previousResult.get(totalDays)).orElse(0L)
                                .equals(Optional.ofNullable(result.get(totalDays)).orElse(0L))) {
                    rank = rankList.get(i - 1);
                } else {
                    rank = i + 1;
                }
            }
            rankList.add(rank);

            // member 찾기 위한 작업
            if (Objects.equals(result.get(QMember.member.id), member.getId())) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1)
            throw new BaseException(BaseResponseStatus.LEAGUE_MEMBER_NOT_FOUND_ERROR);

        return rank;
    }

    @Override
    public Map<String, Integer> getLeagueRankAndTotalSend(Member member, Integer season, Difficulty difficulty) {
        List<Tuple> results = fetchRank(season, difficulty);

        int targetIndex = -1;
        List<Integer> rankList = new ArrayList<>(); // 순위표
        int rank = 1;
        int totalSend = 0;

        // member 탐색, 순위 매기기
        for (int i = 0; i < results.size(); i++) {
            Tuple result = results.get(i);

            // 순위 매기기 작업
            if (i > 0) {
                Tuple previousResult = results.get(i - 1);
                if (Optional.ofNullable(previousResult.get(totalValue)).orElse(0)
                        .equals(Optional.ofNullable(result.get(totalValue)).orElse(0)) &&
                        Optional.ofNullable(previousResult.get(totalDays)).orElse(0L)
                                .equals(Optional.ofNullable(result.get(totalDays)).orElse(0L))) {
                    rank = rankList.get(i - 1);
                } else {
                    rank = i + 1;
                }
            }
            rankList.add(rank);

            // member 찾기 위한 작업
            if (Objects.equals(result.get(QMember.member.id), member.getId())) {
                targetIndex = i;
                totalSend = Optional.ofNullable(result.get(totalValue)).orElse(0);
                break;
            }
        }

        if (targetIndex == -1)
            throw new BaseException(BaseResponseStatus.LEAGUE_MEMBER_NOT_FOUND_ERROR);

        return Map.of("rank", rank, "totalSend", totalSend);
    }

    private List<Tuple> fetchRank(Integer season, Difficulty difficulty) {
        return jpaQueryFactory
                .select(
                        member.id,
                        member.profileImage,
                        member.nickname,
                        totalDays,
                        totalValue,
                        exerciseTimeInSeconds
                )
                .from(league)
                .leftJoin(member).on(league.member.eq(member))
                .leftJoin(record).on(
                        record.member.eq(league.member)
                                .and(record.date.between(LocalDate.now().withDayOfMonth(1), LocalDate.now()))
                )
                .leftJoin(record.difficulties, difficulties)
                .on(difficulties.difficulty.eq(difficulty))
                .where(
                        league.season.eq(season),
                        league.difficulty.eq(difficulty)
                )
                .groupBy(member.id, member.profileImage, member.nickname)
                .orderBy(
                        totalValue.desc(),
                        totalDays.asc()
                )
                .fetch();
    }
}
