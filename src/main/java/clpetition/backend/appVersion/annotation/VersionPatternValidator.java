package clpetition.backend.appVersion.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VersionPatternValidator implements ConstraintValidator<VersionPattern, String> {

    @Override
    public boolean isValid(String version, ConstraintValidatorContext context) {
        return version.matches("^\\d+\\.\\d+\\.\\d+$");
    }
}
