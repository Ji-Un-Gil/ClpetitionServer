package clpetition.backend.gym.controller;

import clpetition.backend.global.annotation.FindMember;
import clpetition.backend.global.response.BaseResponse;
import clpetition.backend.global.response.BaseResponseStatus;
import clpetition.backend.gym.docs.AddFavoriteGymApiDocs;
import clpetition.backend.gym.docs.DeleteFavoriteGymApiDocs;
import clpetition.backend.gym.service.FavoriteGymService;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('USER')")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/gym/favorite")
public class FavoriteGymController implements
        AddFavoriteGymApiDocs,
        DeleteFavoriteGymApiDocs {

    private final FavoriteGymService favoriteGymService;

    @PostMapping("/{gymId}")
    public ResponseEntity<BaseResponse<Void>> addFavoriteGym(
            @FindMember Member member,
            @PathVariable("gymId") Long gymId
    ) {
        favoriteGymService.addFavoriteGym(member, gymId);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.SUCCESS);
    }

    @DeleteMapping("/{gymId}")
    public ResponseEntity<BaseResponse<Void>> deleteFavoriteGym(
            @FindMember Member member,
            @PathVariable("gymId") Long gymId
    ) {
        favoriteGymService.deleteFavoriteGym(member, gymId);
        return BaseResponse.toResponseEntityContainsStatus(BaseResponseStatus.DELETED);
    }
}
