package com.prashant.SmartContactManager.validator;

import java.lang.annotation.*;

import jakarta.validation.Payload;

import jakarta.validation.Constraint;

// This is custom annotation to validate the file

@Documented
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid File";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
