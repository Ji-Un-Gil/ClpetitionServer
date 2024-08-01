package clpetition.backend.record.domain;


import clpetition.backend.global.entity.BaseTimeEntity;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "record_id")
    private List<Difficulties> difficulties;

    private String memo;

    private LocalTime exerciseTime;

    private Integer satisfaction;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> images;

    private Boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
