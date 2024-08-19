package clpetition.backend.gym.repository;

import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.dto.response.GetTargetGymListResponse;
import clpetition.backend.member.domain.Member;

import java.util.List;

public interface GymQueryRepository {
    List<GetTargetGymListResponse> findGymsByMemberAndGymName(Member member, String gymName);
    void plusFavoritesCount(Gym gym);
    void minusFavoritesCount(Gym gym);
}
