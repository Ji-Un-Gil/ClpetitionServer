package clpetition.backend.record.service;

import clpetition.backend.global.infra.file.FileService;
import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.service.GymService;
import clpetition.backend.gym.service.VisitsGymService;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.dto.request.AddRecordRequest;
import clpetition.backend.record.dto.response.GetRecordDetailsResponse;
import clpetition.backend.record.dto.response.GetRecordIdResponse;
import clpetition.backend.record.dto.response.GetRecordStatisticsPerMonthResponse;
import clpetition.backend.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final GymService gymService;
    private final VisitsGymService visitsGymService;
    private final FileService fileService;
    private final RecordRepository recordRepository;

    private final String DATE_PATTERN = "yyyy-M-d";
    private final String TIME_PATTERN = "hh:mm";
    private final String IMAGE_DIR = "record";

    /**
     * 기록 추가
     * */
    public GetRecordIdResponse addRecord(Member member, AddRecordRequest addRecordRequest, List<MultipartFile> multipartFileList) {
        List<String> imageUrlList = fileService.uploadFiles(multipartFileList, IMAGE_DIR);
        Gym gym = gymService.getGym(addRecordRequest.getGymId());
        Record record = toRecord(member, addRecordRequest, gym, imageUrlList);
        visitsGymService.increaseVisitsGym(member, gym);
        return new GetRecordIdResponse(record.getId());
    }

    /**
     * 기록 상세조회
     * */
    @Transactional(readOnly = true)
    public GetRecordDetailsResponse getRecordDetails(Member member, Long recordId) {
        Record record = getRecordWithValidation(member, recordId);
        return toGetRecordDetailsResponse(record);
    }

    /**
     * 기록 수정(삭제 후 저장)
     * */
    public GetRecordIdResponse changeRecord(Member member, Long recordId, AddRecordRequest addRecordRequest, List<MultipartFile> multipartFileList) {
        deleteRecord(member, recordId);
        return addRecord(member, addRecordRequest, multipartFileList);
    }

    /**
     * 기록 삭제
     * */
    public void deleteRecord(Member member, Long recordId) {
        Record record = getRecordWithValidation(member, recordId);
        fileService.deleteFiles(record.getImages());
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
    public Map<LocalDate, List<GetRecordDetailsResponse>> getRecordDetailsPerMonth(Member member, YearMonth yearMonth) {
        List<Record> records = getRecordsPerMonth(member, yearMonth);
        return toGetRecordDetailsPerMonthResponse(records);
    }

    /**
     * 기록 추가 to entity
     * */
    private Record toRecord(Member member, AddRecordRequest addRecordRequest, Gym gym, List<String> imageUrlList) {
        return recordRepository.save(
                Record.builder()
                        .gym(gym)
                        .date(LocalDate.parse(addRecordRequest.getDate(), DateTimeFormatter.ofPattern(DATE_PATTERN)))
                        .difficulties(addRecordRequest.convertDifficulties())
                        .memo(addRecordRequest.getMemo())
                        .exerciseTime(LocalTime.parse(addRecordRequest.getExerciseTime(), DateTimeFormatter.ofPattern(TIME_PATTERN)))
                        .satisfaction(addRecordRequest.getSatisfaction())
                        .images(imageUrlList)
                        .isPrivate(addRecordRequest.getIsPrivate())
                        .member(member)
                        .build()
        );
    }

    /**
     * 기록 상세조회 to dto
     * */
    private GetRecordDetailsResponse toGetRecordDetailsResponse(Record record) {
        return GetRecordDetailsResponse.builder()
                .id(record.getId())
                .gym(gymService.getGymDetails(record.getGym()))
                .date(record.getDate())
                .weekday(record.getDate().getDayOfWeek().getValue())
                .difficulties(GetRecordDetailsResponse.convertDifficulties(record.getDifficulties()))
                .memo(record.getMemo())
                .exerciseTime(record.getExerciseTime())
                .satisfaction(record.getSatisfaction())
                .isPrivate(record.getIsPrivate())
                .imageUrls(record.getImages())
                .build();
    }

    /**
     * (RUD) 기록 가져오기
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
     * 암장 삭제 (hard delete)
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
     * 기록 월별 상세 조회 to map dto
     * */
    private Map<LocalDate, List<GetRecordDetailsResponse>> toGetRecordDetailsPerMonthResponse(List<Record> records) {
        return records.stream()
                .map(this::toGetRecordDetailsResponse)
                .collect(Collectors.groupingBy(GetRecordDetailsResponse::getDate));
    }
}
