package clpetition.backend.member.repository;

import clpetition.backend.member.domain.Profile;
import clpetition.backend.member.dto.request.UpdateProfileRequest;

public interface ProfileQueryRepository {
    void updateProfile(Profile profile, UpdateProfileRequest updateProfileRequest);
}
