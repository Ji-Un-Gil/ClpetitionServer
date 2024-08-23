package clpetition.backend.member.repository;

import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileQueryRepository {
    Optional<Profile> findByMember(Member member);
}
