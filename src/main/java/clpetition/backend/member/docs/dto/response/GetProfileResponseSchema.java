package clpetition.backend.member.docs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "프로필 조회 응답")
public interface GetProfileResponseSchema {

    @Schema(description = "닉네임", example = "위즈")
    String nickname();

    @Schema(description = "프로필 이미지", example = "url", nullable = true)
    String profileImage();

    @Schema(description = "팔로워 수", example = "72")
    Long followerCount();

    @Schema(description = "팔로잉 수", example = "113")
    Long followingCount();

    @Schema(description = "대표 암장명", example = "더클라임 신림점", nullable = true)
    String mainGymName();

    @Schema(description = "키", example = "170", nullable = true)
    Integer height();

    @Schema(description = "리치", example = "150", nullable = true)
    Integer reach();

    @Schema(description = "총 등반기록 수", example = "39")
    Long totalRecord();

    @Schema(description = "클라이밍 시작일", example = "2024-07-01", nullable = true)
    LocalDate startDate();

    @Schema(description = "인스타그램 주소", example = "https://www.instagram.com/example/", nullable = true)
    String instagram();
}
