package clpetition.backend.record.domain;

import clpetition.backend.gym.domain.Difficulty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Difficulties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer value;

    public Difficulties(Difficulty difficulty, Integer value) {
        this.difficulty = difficulty;
        this.value = value;
    }
}
