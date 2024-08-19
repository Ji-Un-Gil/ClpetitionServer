package clpetition.backend.gym.domain;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    YELLOW("노랑", "V0"),
    ORANGE("주황", "V1-2"),
    GREEN("초록", "V2-3"),
    BLUE("파랑", "V3-4"),
    RED("빨강", "V4-5"),
    PURPLE("보라", "V5-6"),
    GRAY("회색", "V6-7"),
    BROWN("갈색", "V7-8"),
    BLACK("검정", "V8+"),
    PINK("분홍", "V8+"),
    NAVY("남색", "V8+"),
    WHITE("흰색", "V8+"),
    ;

    private final String key;
    private final String description;

    public static Difficulty findByKey(String key) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.getKey().equals(key))
                return difficulty;
        }
        throw new BaseException(BaseResponseStatus.DIFFICULTY_NOT_FOUND_ERROR);
    }
}
