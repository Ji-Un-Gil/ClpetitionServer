package clpetition.backend.gym.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.FavoriteGym;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.repository.FavoriteGymRepository;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteGymService {

    private final GymService gymService;
    private final FavoriteGymRepository favoriteGymRepository;

    /**
     * 암장 관심 등록
     * */
    public void addFavoriteGym(Member member, Long gymId) {
        Gym gym = gymService.getGym(gymId);
        saveFavoriteGym(member, gym);
        gymService.increaseFavoriteGym(gym);
    }

    /**
     * 암장 관심 해제
     * */
    public void deleteFavoriteGym(Member member, Long gymId) {
        Gym gym = gymService.getGym(gymId);
        FavoriteGym favoriteGym = getFavoriteGym(member, gym);
        gymService.decreaseFavoriteGym(gym);
        deleteFavoriteGym(favoriteGym);
    }

    /**
     * 암장 관심 등록 여부 가져오기
     * */
    public boolean isFavoriteGym(Member member, Gym gym) {
        return isExistFavoriteGym(member, gym);
    }

    /**
     * 암장 관심 등록 (저장)
     * */
    private void saveFavoriteGym(Member member, Gym gym) {
        favoriteGymRepository.save(
                FavoriteGym.builder()
                        .member(member)
                        .gym(gym)
                        .build()
        );
    }

    /**
     * 암장 관심 객체 가져오기 (관심 등록이 되어있는 상태)
     * 1. 없을 경우 예외
     * */
    private FavoriteGym getFavoriteGym(Member member, Gym gym) {
        return favoriteGymRepository.findByMemberAndGym(member, gym)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAVORITE_GYM_NOT_FOUND_ERROR));
    }

    /**
     * 암장 관심 객체 삭제 (hard delete)
     * */
    private void deleteFavoriteGym(FavoriteGym favoriteGym) {
        favoriteGymRepository.deleteById(favoriteGym.getId());
    }

    /**
     * 암장 관심 등록 여부 가져오기
     * */
    private boolean isExistFavoriteGym(Member member, Gym gym) {
        return favoriteGymRepository.existsByMemberAndGym(member, gym);
    }
}
