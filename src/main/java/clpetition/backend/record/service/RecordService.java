package clpetition.backend.record.service;

import clpetition.backend.global.infra.file.FileService;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.service.FavoriteGymService;
import clpetition.backend.gym.service.GymService;
import clpetition.backend.gym.service.VisitsGymService;
import clpetition.backend.league.service.LeagueService;
import clpetition.backend.member.domain.Member;
import clpetition.backend.member.dto.response.GetRecordHistoryPageResponse;
import clpetition.backend.record.domain.Difficulties;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.domain.RecordImages;
import clpetition.backend.record.dto.request.AddRecordRequest;
import clpetition.backend.record.dto.response.*;
import clpetition.backend.record.repository.DifficultiesRepository;
import clpetition.backend.record.repository.RecordImagesRepository;
import clpetition.backend.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final GymService gymService;
    private final VisitsGymService visitsGymService;
    private final FavoriteGymService favoriteGymService;
    private final FileService fileService;
    private final LeagueService leagueService;
    private final RecordRepository recordRepository;
    private final RecordImagesRepository recordImagesRepository;
    private final DifficultiesRepository difficultiesRepository;

    private final String DATE_PATTERN = "yyyy-M-d";
    private final String TIME_PATTERN = "HH:mm";
    private final String IMAGE_DIR = "record";

    /**
     * 기록 추가
     * */
    public GetRecordIdResponse addRecord(Member member, AddRecordRequest addRecordRequest, List<MultipartFile> multipartFileList) {
        // 미래일 추가 불가
        // isValidDate(LocalDate.parse(addRecordRequest.getDate(), DateTimeFormatter.ofPattern(DATE_PATTERN)));
        List<String> imageUrlList = fileService.uploadFiles(multipartFileList, IMAGE_DIR);
        Gym gym = gymService.getGym(addRecordRequest.getGymId());
        Record record = toRecord(member, addRecordRequest, gym, imageUrlList);
        visitsGymService.increaseVisitsGym(member, gym);
        return new GetRecordIdResponse(record.getId(), imageUrlList);
    }

    /**
     * 기록 상세조회
     * */
    @Transactional(readOnly = true)
    public GetRecordDetailsResponse getRecordDetails(Member member, Long recordId) {
        Record record = getRecordById(recordId);
        Hibernate.initialize(record.getImages());
        Member recordMember = record.getMember();
        // 본인 기록 조회
        if (member.getId().equals(recordMember.getId()))
            return toGetRecordDetailsResponse(record, null);
        // 타인 기록 조회
        if (record.getIsPrivate())
            throw new BaseException(BaseResponseStatus.RECORD_NOT_FOUND_ERROR);

        Map<String, Object> leagueBrief = leagueService.getLeagueBrief(recordMember);
        return toGetRecordDetailsResponseOthers(record, null, recordMember, leagueBrief);
    }

    /**
     * 기록 수정(삭제 후 저장)
     * */
    public GetRecordIdResponse changeRecord(Member member, Long recordId, AddRecordRequest addRecordRequest) {
        // 미래일 추가 불가
        // isValidDate(LocalDate.parse(addRecordRequest.getDate(), DateTimeFormatter.ofPattern(DATE_PATTERN)));

        Record record = getRecordWithValidation(member, recordId);
        Hibernate.initialize(record.getImages());
        List<String> imageUrlList = Record.convertToImageUrls(record.getImages());

        visitsGymService.decreaseVisitsGym(member, record.getGym());
        deleteRecordById(record);

        Gym gym = gymService.getGym(addRecordRequest.getGymId());
        Record newRecord = toRecord(member, addRecordRequest, gym, imageUrlList);
        visitsGymService.increaseVisitsGym(member, gym);
        return new GetRecordIdResponse(newRecord.getId(), imageUrlList);
    }

    /**
     * 기록 삭제
     * */
    public void deleteRecord(Member member, Long recordId) {
        Record record = getRecordWithValidation(member, recordId);
        fileService.deleteFiles(Record.convertToImageUrls(record.getImages()));
        visitsGymService.decreaseVisitsGym(member, record.getGym());
        deleteRecordById(record);
    }

    /**
     * 기록 월별 통계 조회
     * */
    @Transactional(readOnly = true)
    public GetRecordStatisticsPerMonthResponse getRecordStatisticsPerMonth(Member member, YearMonth yearMonth) {
        return toGetRecordStatisticsPerMonthResponse(member, yearMonth);
    }

    /**
     * 기록 월별 상세 조회
     * */
    @Transactional(readOnly = true)
    public List<GetRecordDetailsResponse> getRecordDetailsPerMonth(Member member, YearMonth yearMonth) {
        List<Record> records = getRecordsPerMonth(member, yearMonth);
        records.forEach(record -> Hibernate.initialize(record.getImages()));
        return toGetRecordDetailsListResponse(records);
    }

    /**
     * 암장 정보 및 관련 암장 기록 가져오기
     * */
    @Transactional(readOnly = true)
    public GetGymInfoAndRelatedRecordResponse getGymInfoAndRelatedRecord(Member member, Long gymId) {
        Gym gym = gymService.getGym(gymId);
        return toGetGymInfoAndRelatedRecordResponse(member, gym);
    }

    /**
     * 메인 통계 가져오기
     * */
    @Transactional(readOnly = true)
    public GetMainStatisticsResponse getMainStatistics(Member member, YearMonth yearMonth) {
        return toGetMainStatisticsResponse(member, yearMonth);
    }

    /**
     * 메인 이번달 등반 내역 가져오기
     * */
    @Transactional(readOnly = true)
    public GetMainHistoryResponse getMainHistory(Member member, YearMonth yearMonth) {
        return toGetMainHistoryResponse(member, yearMonth);
    }

    /**
     * 사용자의 총 등반 기록 수 가져오기
     * */
    public Long getTotalRecord(Member member) {
        return findTotalRecord(member);
    }

    /**
     * 사용자의 등반기록 최신순으로 가져오기
     * */
    public GetRecordHistoryPageResponse getRecordHistory(Member member, Long lastRecordId, boolean isMyself) {
        Map<String, Object> recordHistoryMap = findRecordHistory(member, lastRecordId, isMyself);
        List<Record> recordList = (List<Record>) recordHistoryMap.get("recordHistory");
        List<GetRecordDetailsResponse> recordHistory = new ArrayList<>();
        for (Record record : recordList)
            recordHistory.add(toGetRecordDetailsResponse(record, null));

        return GetRecordHistoryPageResponse.builder()
                .hasNext((Boolean) recordHistoryMap.get("hasNext"))
                .recordHistory(recordHistory)
                .build();
    }

    /**
     * 기록 최신순으로 전체 상세 조회
     * */
    @Transactional(readOnly = true)
    public List<GetRecordDetailsResponse> getRecordDetailsAll(Member member) {
        List<Record> records = getRecordsAllLatest(member);
        records.forEach(record -> Hibernate.initialize(record.getImages()));
        return toGetRecordDetailsListResponse(records);
    }

    /**
     * 기록 전체 가져오기
     * */
    public List<Record> getRecordsAll(Member member) {
        return getRecordsAllByMember(member);
    }

    /**
     * 기록할 일자가 미래인 경우 오류 (정책 변경으로 보류)
     * */
    private void isValidDate(LocalDate date) {
        if (date.isAfter(LocalDate.now()))
            throw new BaseException(BaseResponseStatus.RECORD_DATE_ERROR);
    }

    /**
     * 기록 추가 to entity
     * */
    private Record toRecord(Member member, AddRecordRequest addRecordRequest, Gym gym, List<String> imageUrlList) {
        Record record = recordRepository.save(
                Record.builder()
                        .gym(gym)
                        .date(LocalDate.parse(addRecordRequest.getDate(), DateTimeFormatter.ofPattern(DATE_PATTERN)))
                        .memo(addRecordRequest.getMemo())
                        .exerciseTime(LocalTime.parse(addRecordRequest.getExerciseTime(), DateTimeFormatter.ofPattern(TIME_PATTERN)))
                        .satisfaction(addRecordRequest.getSatisfaction())
                        .isPrivate(addRecordRequest.getIsPrivate())
                        .member(member)
                        .build()
        );
        List<Difficulties> difficulties = Difficulties.convertToDifficulties(addRecordRequest.getDifficulties(), record);
        List<RecordImages> recordImages = Record.convertToRecordImages(imageUrlList, record);
        difficultiesRepository.saveAll(difficulties);
        recordImagesRepository.saveAll(recordImages);
        return record;
    }

    /**
     * 기록 상세조회 to dto (myself)
     * */
    private GetRecordDetailsResponse toGetRecordDetailsResponse(Record record, String shortName) {
        return GetRecordDetailsResponse.builder()
                .recordId(record.getId())
                .gym(gymService.getGymDetails(record.getGym(), shortName))
                .date(record.getDate())
                .weekday(record.getDate().getDayOfWeek().getValue())
                .difficulties(Difficulties.convertToDifficultiesMap(record.getDifficulties()))
                .memo(record.getMemo())
                .exerciseTime(record.getExerciseTime())
                .satisfaction(record.getSatisfaction())
                .isPrivate(record.getIsPrivate())
                .imageUrls(Record.convertToImageUrls(record.getImages()))
                .build();
    }

    /**
     * 기록 상세조회 to dto (others)
     * */
    private GetRecordDetailsResponse toGetRecordDetailsResponseOthers(Record record, String shortName, Member member, Map<String, Object> leagueBrief) {
        return GetRecordDetailsResponse.builder()
                .recordId(record.getId())
                .gym(gymService.getGymDetails(record.getGym(), shortName))
                .date(record.getDate())
                .weekday(record.getDate().getDayOfWeek().getValue())
                .difficulties(Difficulties.convertToDifficultiesMap(record.getDifficulties()))
                .memo(record.getMemo())
                .exerciseTime(record.getExerciseTime())
                .satisfaction(record.getSatisfaction())
                .isPrivate(record.getIsPrivate())
                .imageUrls(Record.convertToImageUrls(record.getImages()))
                .memberId(member.getId())
                .profileImageUrl(member.getProfileImage())
                .nickname(member.getNickname())
                .difficulty(!leagueBrief.get("difficulty").equals(Optional.empty()) ? leagueBrief.get("difficulty").toString() : null)
                .rank(!leagueBrief.get("rank").equals(Optional.empty()) ? leagueBrief.get("rank").toString() : null)
                .build();
    }

    /**
     * (R) 기록 가져오기
     * 1. 요청 기록 존재여부 확인
     * */
    private Record getRecordById(Long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RECORD_NOT_FOUND_ERROR));
    }

    /**
     * (UD) 기록 가져오기
     * 1. 요청 기록 존재여부 확인
     * 2. 요청 사용자와 요청 기록의 소유자 동일인인지 확인
     * */
    private Record getRecordWithValidation(Member member, Long recordId) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RECORD_NOT_FOUND_ERROR));

        if (!member.getId().equals(record.getMember().getId()))
            throw new BaseException(BaseResponseStatus.RECORD_NOT_FOUND_ERROR);

        return record;
    }

    /**
     * 기록 삭제 (hard delete)
     * */
    private void deleteRecordById(Record record) {
        recordRepository.deleteById(record.getId());
    }

    /**
     * 기록 월별 통계 조회 to dto
     * */
    private GetRecordStatisticsPerMonthResponse toGetRecordStatisticsPerMonthResponse(Member member, YearMonth yearMonth) {
        return recordRepository.findRecordStatisticsPerMonth(member, yearMonth);
    }

    /**
     * (R) 월별 상세 기록 가져오기
     * */
    private List<Record> getRecordsPerMonth(Member member, YearMonth yearMonth) {
        return recordRepository.findRecordsPerMonth(member, yearMonth);
    }

    /**
     * 기록 상세 조회(월별, 전체) to list dto
     * */
    private List<GetRecordDetailsResponse> toGetRecordDetailsListResponse(List<Record> records) {
        return records.stream()
                .map(record -> toGetRecordDetailsResponse(record, record.getGym().getShortName()))
                .collect(Collectors.toList());
    }

    /**
     * 관련 암장 기록 가져오기
     * */
    private List<GetRelatedRecordResponse> getRelatedRecord(Gym gym) {
        return recordRepository.findRelatedRecord(gym);
    }

    /**
     * 암장 관심 등록 여부 가져오기
     * */
    private boolean isFavoriteGym(Member member, Gym gym) {
        return favoriteGymService.isFavoriteGym(member, gym);
    }

    /**
     * 암장 정보 및 관련 암장 기록 가져오기 to dto
     * */
    private GetGymInfoAndRelatedRecordResponse toGetGymInfoAndRelatedRecordResponse(Member member, Gym gym) {
        return GetGymInfoAndRelatedRecordResponse.builder()
                .relatedRecord(getRelatedRecord(gym))
                .gymName(gym.getName())
                .region(gym.getRegion())
                .favorites(gym.getFavorites())
                .isFavorite(isFavoriteGym(member, gym))
                .build();
    }

    /**
     * 메인 통계 가져오기 to dto
     * */
    private GetMainStatisticsResponse toGetMainStatisticsResponse(Member member, YearMonth yearMonth) {
        return recordRepository.findMainStatistics(member, yearMonth);
    }

    /**
     * 메인 이번달 등반 내역 가져오기 to dto
     * */
    private GetMainHistoryResponse toGetMainHistoryResponse(Member member, YearMonth yearMonth) {
        return recordRepository.findMainHistory(member, yearMonth);
    }

    /**
     * (R) 사용자의 총 등반 기록 수 가져오기
     * */
    private Long findTotalRecord(Member member){
        return recordRepository.countByMember(member);
    }

    /**
     * (R) 사용자의 등반기록 최신순으로 가져오기
     * */
    private Map<String, Object> findRecordHistory(Member member, Long lastRecordId, boolean isMyself) {
        return recordRepository.findRecordHistory(member, lastRecordId, isMyself);
    }

    /**
     * (R) 전체 상세 기록 최신순으로 가져오기
     * */
    private List<Record> getRecordsAllLatest(Member member) {
        return recordRepository.findByMemberOrderByDateDesc(member);
    }

    /**
     * (D) 전체 상세 기록 가져오기
     * */
    private List<Record> getRecordsAllByMember(Member member) {
        return recordRepository.findByMember(member);
    }
}
