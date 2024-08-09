package clpetition.backend.gym.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.dto.response.GetGymDetailsResponse;
import clpetition.backend.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GymService {

    private final GymRepository gymRepository;

    /**
     * 암장 가져오기
     * */
    public Gym getGym(Long gymId) {
        return getGymById(gymId);
    }

    /**
     * 암장 관심 수 수정 (+1)
     * */
    public void increaseFavoriteGym(Gym gym) {
        increaseFavorites(gym);
    }

    /**
     * 암장 관심 수 수정 (-1)
     * */
    public void decreaseFavoriteGym(Gym gym) {
        decreaseFavorites(gym);
    }

    /**
     * 암장 상세조회
     * */
    public GetGymDetailsResponse getGymDetails(Gym gym) {
        return toGetGymDetailsResponse(gym);
    }

    /**
     * 암장 ID로 암장 가져오기
     * */
    private Gym getGymById(Long gymId) {
        return gymRepository.findById(gymId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.GYM_NOT_FOUND_ERROR));
    }

    /**
     * 암장 관심 수 +1
     * */
    private void increaseFavorites(Gym gym) {
        gymRepository.save(
                Gym.builder()
                        .id(gym.getId())
                        .brand(gym.getBrand())
                        .branch(gym.getBranch())
                        .address(gym.getAddress())
                        .favorites(gym.getFavorites() + 1)
                        .build()
        );
    }

    /**
     * 암장 관심 수 -1
     * */
    private void decreaseFavorites(Gym gym) {
        gymRepository.save(
                Gym.builder()
                        .id(gym.getId())
                        .brand(gym.getBrand())
                        .branch(gym.getBranch())
                        .address(gym.getAddress())
                        .favorites(gym.getFavorites() - 1)
                        .build()
        );
    }

    /**
     * 암장 상세조회 to dto
     * */
    private GetGymDetailsResponse toGetGymDetailsResponse(Gym gym) {
        return GetGymDetailsResponse.builder()
                .id(gym.getId())
                .brand(gym.getBrand())
                .branch(gym.getBranch())
                .build();
    }
}
