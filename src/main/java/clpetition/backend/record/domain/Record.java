package clpetition.backend.record.domain;


import clpetition.backend.global.entity.BaseTimeEntity;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Record extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    // 요일은 필요할 때 로직에서 계산해 처리
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "record")
    private List<Difficulties> difficulties;

    private String memo;

    private LocalTime exerciseTime;

    private Integer satisfaction;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "record")
    private List<RecordImages> images;

    private Boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static List<String> convertToImageUrls(List<RecordImages> recordImages) {
        return recordImages.stream()
                .map(RecordImages::getImageUrl)
                .collect(Collectors.toList());
    }

    public static List<RecordImages> convertToRecordImages(List<String> imageUrls, Record record) {
        return imageUrls.stream()
                .map(url -> RecordImages.builder()
                        .imageUrl(url)
                        .record(record)
                        .build())
                .collect(Collectors.toList());
    }
}
