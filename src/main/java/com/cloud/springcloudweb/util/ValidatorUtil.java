package com.cloud.springcloudweb.util;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorUtil {
    public static <T> Errors validate(Validator validator, T validObject) {
        Errors errors = new BeanPropertyBindingResult(validObject, validObject.getClass().getName());
        validator.validate(validObject, errors);
        return errors;
    }
}
