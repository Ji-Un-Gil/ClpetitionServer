package clpetition.backend.record.domain;

import clpetition.backend.gym.domain.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    public static Map<String, Integer> convertToDifficultiesMap(List<Difficulties> difficulties) {
        return difficulties.stream()
                .collect(Collectors.toMap(
                        difficulty -> difficulty.getDifficulty().getKey(),
                        Difficulties::getValue
                ));
    }

    public static List<Difficulties> convertToDifficulties(Map<String, Integer> difficulties, Record record) {
        return difficulties.entrySet().stream()
                .map(entry -> Difficulties.builder()
                        .difficulty(Difficulty.findByKey(entry.getKey()))
                        .value(entry.getValue())
                        .record(record)
                        .build())
                .collect(Collectors.toList());
    }
}
