package com.cloud.springcloudweb.validator;

import com.cloud.springcloudweb.dto.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import reactor.core.publisher.Mono;

public abstract class AbstractValidationHandler<T, U extends Validator> {
    private final Class<T> validationClass;
    private final U validator;

    protected AbstractValidationHandler(Class<T> clazz, U validator) {
        this.validationClass = clazz;
        this.validator = validator;
    }

    abstract protected Mono<ResponseDto> processBody(Class<T> validBody);

    protected Mono<ResponseDto> onValidationErrors(Errors errors, Class<T> invalidBody) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getAllErrors().toString());
    }

    public final Mono<ResponseDto> handleRequest() {
        return Mono.just(this.validationClass)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
                    this.validator.validate(body, errors);

                    return (this.isNotError(errors)) ? processBody(body) : onValidationErrors(errors, body);
                });
    }

    private boolean isNotError(Errors errors){
        return (Objects.isNull(errors) || errors.getAllErrors().isEmpty());
    }
}
