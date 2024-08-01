package clpetition.backend.record.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.repository.GymRepository;
import clpetition.backend.member.domain.Member;
import clpetition.backend.record.controller.dto.request.AddRecordRequest;
import clpetition.backend.record.controller.dto.response.GetRecordIdResponse;
import clpetition.backend.record.domain.Record;
import clpetition.backend.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final RecordRepository recordRepository;
    private final GymRepository gymRepository;

    private final String DATE_PATTERN = "yyyy-M-d";
    private final String TIME_PATTERN = "hh:mm";

    public GetRecordIdResponse addRecord(Member member, AddRecordRequest addRecordRequest) {
        Record record = recordRepository.save(toRecord(member, addRecordRequest));
        return new GetRecordIdResponse(record.getId());
    }

    private Record toRecord(Member member, AddRecordRequest addRecordRequest) {
        return Record.builder()
                .gym(gymRepository.findById(addRecordRequest.getGymId())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.GYM_NOT_FOUND_ERROR)))
                .date(LocalDate.parse(addRecordRequest.getDate(), DateTimeFormatter.ofPattern(DATE_PATTERN)))
                .difficulties(addRecordRequest.convertDifficulties())
                .memo(addRecordRequest.getMemo())
                .exerciseTime(LocalTime.parse(addRecordRequest.getExerciseTime(), DateTimeFormatter.ofPattern(TIME_PATTERN)))
                .satisfaction(addRecordRequest.getSatisfaction())
                // .images()
                .isPrivate(addRecordRequest.getIsPrivate())
                .member(member)
                .build();
    }
}
