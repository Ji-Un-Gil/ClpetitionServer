package clpetition.backend.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimePatternValidator implements ConstraintValidator<LocalTimePattern, String> {
    private String pattern;

    @Override
    public void initialize(LocalTimePattern constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // hh:mm
        try {
            LocalTime.parse(value, DateTimeFormatter.ofPattern(this.pattern));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
