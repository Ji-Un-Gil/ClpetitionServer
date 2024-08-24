package clpetition.backend.record.repository;

import clpetition.backend.record.domain.Difficulties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultiesRepository extends JpaRepository<Difficulties, Long> {
}
