package clpetition.backend.gym.repository;

import clpetition.backend.gym.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long>, GymQueryRepository {

    boolean existsByNameOrAddress(String name, String address);
}
