package clpetition.backend.gym.repository;

import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.domain.VisitsGym;
import clpetition.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitsGymRepository extends JpaRepository<VisitsGym, Long> {
    Optional<VisitsGym> findByMemberAndGym(Member member, Gym gym);
}
