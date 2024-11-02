package clpetition.backend.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@Builder
@RedisHash("instagramAccessToken")
public class InstagramAccessToken {

    public static final Long DEFAULT_TTL = 5183944L;

    @Id
    private Long id;

    @Indexed
    private String token;

    @TimeToLive
    private Long expiration;

    public static InstagramAccessToken createInstagramAccessToken(Long userId, String token) {
        return InstagramAccessToken.builder()
                .id(userId)
                .token(token)
                .expiration(DEFAULT_TTL)
                .build();
    }

    public void updateInstagramAccessToken(String token) {
        this.token = token;
    }

}
