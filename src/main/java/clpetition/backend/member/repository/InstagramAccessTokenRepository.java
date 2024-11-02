package clpetition.backend.member.repository;

import clpetition.backend.member.domain.InstagramAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface InstagramAccessTokenRepository extends CrudRepository<InstagramAccessToken, Long> {
}
