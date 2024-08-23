package clpetition.backend.member.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = NicknamePatternValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface NicknamePattern {

    String message() default "입력값에 한글, 영문, 숫자가 아닌 값이 포함되었거나, 띄어쓰기가 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
