package clpetition.backend.league.domain;

import clpetition.backend.global.entity.BaseTimeEntity;
import clpetition.backend.gym.domain.Difficulty;
import clpetition.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class League extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer season;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static final Integer SEASON = (LocalDate.now().getYear() - 2024) * 12 + (LocalDate.now().getMonthValue() - 9) + 1;

    @PrePersist
    private void calculateSeason() {
        if (this.season == null)
            this.season = SEASON;
    }
}
