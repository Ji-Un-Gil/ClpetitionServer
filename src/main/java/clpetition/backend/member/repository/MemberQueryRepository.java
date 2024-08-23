package clpetition.backend.member.repository;

import clpetition.backend.member.domain.Member;

public interface MemberQueryRepository {
    void updateNicknameAndProfileImage(Member member, String nickname, String imageUrl);
}
