package com.cloud.springcloudweb.service;

import com.cloud.springcloudweb.domain.dto.UserDto;
import com.cloud.springcloudweb.domain.model.User;
import com.cloud.springcloudweb.exception.UserNotFoundException;
import com.cloud.springcloudweb.exception.ValidationException;
import com.cloud.springcloudweb.repository.UserRepository;
import com.cloud.springcloudweb.util.ValidatorUtil;
import com.cloud.springcloudweb.validator.UserDtoValidator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Validator userDtoValidator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userDtoValidator = new UserDtoValidator();
    }

    public Flux<User> findAll() {
        return Flux.fromIterable(userRepository.findAll())
                .subscribeOn(Schedulers.elastic());
    }

    public Mono<User> find(Long id) {
        return Mono.just(userRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .onErrorResume(e -> Mono.error(new UserNotFoundException(id)))
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .subscribeOn(Schedulers.elastic());
    }

    public Mono<UserDto> save(Mono<UserDto> userDto) {
        return userDto
                .map(user -> {
                    Errors errors = ValidatorUtil.validate(userDtoValidator, user);

                    if (errors.hasErrors()) {
                        throw new ValidationException(errors);
                    }

                    return user;
                })
                .doOnNext(it -> userRepository.save(User.builder().name(it.getName()).build()))
                .doOnSuccess(it -> log.info("사용자 추가에 성공하였습니다. : {}", it.toString()))
                .doOnError(it -> log.error("사용자 추가에 실패하였습니다. : {}", it.toString()))
                .subscribeOn(Schedulers.elastic());
    }
}
