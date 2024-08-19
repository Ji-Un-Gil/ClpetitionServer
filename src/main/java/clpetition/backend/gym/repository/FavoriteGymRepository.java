package clpetition.backend.gym.repository;

import clpetition.backend.gym.domain.FavoriteGym;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteGymRepository extends JpaRepository<FavoriteGym, Long> {
    Optional<FavoriteGym> findByMemberAndGym(Member member, Gym gym);
    boolean existsByMemberAndGym(Member member, Gym gym);
}
