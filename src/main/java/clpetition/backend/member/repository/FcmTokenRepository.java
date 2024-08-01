package clpetition.backend.member.repository;

import clpetition.backend.member.domain.FcmToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FcmTokenRepository extends CrudRepository<FcmToken, Long> {
    @Override
    List<FcmToken> findAll();
}
