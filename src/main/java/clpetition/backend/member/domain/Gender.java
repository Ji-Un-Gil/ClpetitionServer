package clpetition.backend.member.domain;

import clpetition.backend.global.response.BaseException;
import clpetition.backend.global.response.BaseResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자")
    ;

    private final String key;

    public static Gender findByKey(String key) {
        if (Gender.MALE.getKey().equals(key))
            return Gender.MALE;
        if (Gender.FEMALE.getKey().equals(key))
            return Gender.FEMALE;
        throw new BaseException(BaseResponseStatus.GENDER_NOT_FOUND_ERROR);
    }
}
