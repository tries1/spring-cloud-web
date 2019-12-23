package com.cloud.springcloudweb.controller;

import com.cloud.springcloudweb.dto.UserDto;
import com.cloud.springcloudweb.exception.UserNotFoundException;
import com.cloud.springcloudweb.exception.ValidationException;
import com.cloud.springcloudweb.model.User;
import com.cloud.springcloudweb.service.UserService;
import com.cloud.springcloudweb.validator.UserDtoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
//@RestController
//@RequestMapping("api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public Mono<User> findUser(@PathVariable Long id) throws UserNotFoundException {
        return userService.find(id);
    }

    /**
     * An Errors/BindingResult argument is expected immediately after the @ModelAttribute argument to which it applies.
     * For @RequestBody and @RequestPart arguments, please declare them with a reactive type wrapper and use its onError operators to handle WebExchangeBindException:
     * public reactor.core.publisher.Mono com.cloud.springcloudweb.controller.UserController.saveUser(com.cloud.springcloudweb.dto.UserDto,org.springframework.validation.BindingResult) throws org.springframework.validation.BindException
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserDto> saveUser(@RequestBody UserDto userDto) {
        Validator userDtoValidator = new UserDtoValidator();
        return Mono.just(userDto)
        .map(user -> {
            Errors errors = new BeanPropertyBindingResult(user, UserDto.class.getName());
            userDtoValidator.validate(user, errors);

            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            } else{
                //userService.save(user);
            }

            return user;
        });
    }
}
