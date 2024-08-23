package clpetition.backend.member.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknamePatternValidator implements ConstraintValidator<NicknamePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[가-힣a-zA-Z0-9]*$");
    }
}
