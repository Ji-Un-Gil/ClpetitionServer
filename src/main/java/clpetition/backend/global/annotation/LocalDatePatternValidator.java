package clpetition.backend.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class LocalDatePatternValidator implements ConstraintValidator<LocalDatePattern, String> {

    private String pattern;
    private boolean nullable;

    @Override
    public void initialize(LocalDatePattern constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return nullable;

        // yyyy-MM-dd
        if (value.length() > 7) {
            try {
                LocalDate.parse(value, DateTimeFormatter.ofPattern(this.pattern));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        // yyyy-MM
        try {
            YearMonth.parse(value, DateTimeFormatter.ofPattern(this.pattern));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
