package com.example.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validation annotation to validate that 3 password fields have correct length.
 *
 * Example, compare 1 pair of fields:
 * @PasswordLength(
 *         first = "oldPassword",
 *         second = "newPassword",
 *         third = "confirmPassword",
 *         min = 8, max = 200,
 *         message = "The password must be at least 8 characters long"
 * )
 *
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordLengthValidator.class)
@Documented
public @interface PasswordLength {
    String message() default "{com.example.app.validator.PasswordLength.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * @return The first field
     */
    String first();

    /**
     * @return The second field
     */
    String second();

    /**
     * @return The third field
     */
    String third();

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    /**
     * Defines several <code>@PasswordLength</code> annotations on the same element
     *
     * @see PasswordLength
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        PasswordLength[] value();
    }
}
