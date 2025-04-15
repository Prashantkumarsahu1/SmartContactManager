package com.prashant.SmartContactManager.validator;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// TO validate the file size and type..... we create a custom annotation and a custom validator
public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
   
    //Size
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB
   
    // type

    //height

    //width

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) { 
        
        if (file == null ||file.isEmpty()) {
            //context.disableDefaultConstraintViolation();
            //context.buildConstraintViolationWithTemplate("File is empty").addConstraintViolation();
            return true;
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size is more than 2MB").addConstraintViolation();
            return false;
        }
        return true;
    }

}
