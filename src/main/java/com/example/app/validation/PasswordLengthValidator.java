package com.example.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class PasswordLengthValidator implements ConstraintValidator<PasswordLength, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String thirdFieldName;
    private int min;
    private int max;
    private String message;

    @Override
    public void initialize(final PasswordLength constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        thirdFieldName = constraintAnnotation.third();
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final String firstObj = BeanUtils.getProperty(value, firstFieldName);
            final String secondObj = BeanUtils.getProperty(value, secondFieldName);
            final String thirdObj = BeanUtils.getProperty(value, thirdFieldName);

            valid = (firstObj == null && secondObj == null && thirdObj == null) ||
                    (firstObj.length() == 0 && secondObj.length() == 0 && thirdObj.length() == 0) ||
                   (firstObj.length() >= min && firstObj.length() < max &&
                    secondObj.length() >= min && secondObj.length() < max &&
                    thirdObj.length() >= min && thirdObj.length() < max);

        } catch (final Exception ignore) {
            // ignore
        }
        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
