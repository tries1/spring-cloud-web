package com.cloud.springcloudweb.validator;

import com.cloud.springcloudweb.dto.UserDto;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

public class UserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "name은 필수입니다.");

        UserDto request = (UserDto) target;
        if (Objects.isNull(request.getId()) || request.getId() < 0) {
            errors.rejectValue("id", "field.min.length", new Object[]{Integer.valueOf(0)}, "id는 null 또는 0 이하일수 없습니다.");
        }
    }
}
