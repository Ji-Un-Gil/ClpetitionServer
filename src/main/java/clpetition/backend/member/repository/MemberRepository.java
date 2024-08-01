package clpetition.backend.member.repository;

import clpetition.backend.member.domain.Member;
import clpetition.backend.member.domain.Role;
import clpetition.backend.member.domain.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Boolean existsByNicknameAndRole(String nickname, Role role);
    Optional<Member> findBySocialId(String socialId);
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
    Boolean existsBySocialTypeAndSocialId(SocialType socialType, String socialId);
}