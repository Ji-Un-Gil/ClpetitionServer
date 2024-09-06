package clpetition.backend.gym.service;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.dto.request.AddGymRequest;
import clpetition.backend.gym.dto.response.GetGymDetailsResponse;
import clpetition.backend.gym.dto.response.GetGymIdResponse;
import clpetition.backend.gym.dto.response.GetTargetGymListResponse;
import clpetition.backend.gym.repository.GymRepository;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public GetGymDetailsResponse getGymDetails(Gym gym, String shortName) {
        return toGetGymDetailsResponse(gym, shortName);
    }
    
    /**
     * 암장명 리스트 조회
     * */
    public List<GetTargetGymListResponse> getTargetGymList(Member member, String gymName) {
        return toGetTargetGymListResponseList(member, gymName);
    }

    /**
     * 암장 추가
     * */
    public GetGymIdResponse addGym(AddGymRequest addGymRequest) {
        validGym(addGymRequest.name(), addGymRequest.address());
        return toGetGymIdResponse(
                saveGym(addGymRequest)
        );
    }

    /**
     * 암장 추가 시 중복 체크
     * */
    private void validGym(String name, String address) {
        if (gymRepository.existsByNameOrAddress(name, address))
            throw new BaseException(BaseResponseStatus.GYM_ALREADY_EXISTS_ERROR);
    }

    /**
     * 암장 저장
     * */
    private Long saveGym(AddGymRequest addGymRequest) {
        return gymRepository.save(
                Gym.builder()
                        .name(addGymRequest.name())
                        .region(addGymRequest.region())
                        .address(addGymRequest.address())
                        .shortName(addGymRequest.shortName())
                        .favorites(0L)
                        .build()
        ).getId();
    }

    /**
     * 암장 추가 to dto
     * */
    private GetGymIdResponse toGetGymIdResponse(Long id) {
        return GetGymIdResponse.builder()
                .gymId(id)
                .build();
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
        gymRepository.plusFavoritesCount(gym);
    }

    /**
     * 암장 관심 수 -1
     * */
    private void decreaseFavorites(Gym gym) {
        gymRepository.minusFavoritesCount(gym);
    }

    /**
     * 암장 상세조회 to dto
     * */
    private GetGymDetailsResponse toGetGymDetailsResponse(Gym gym, String shortName) {
        return GetGymDetailsResponse.builder()
                .gymId(gym.getId())
                .gymName(gym.getName())
                .shortName(shortName != null ? (shortName.length() > 5 ? shortName.substring(0,4) + "…" : shortName) : null)
                .build();
    }

    /**
     * 암장명 리스트 조회 to dto
     * */
    private List<GetTargetGymListResponse> toGetTargetGymListResponseList(Member member, String gymName) {
        return gymRepository.findGymsByMemberAndGymName(member, gymName);
    }
}
