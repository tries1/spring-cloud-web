package com.cloud.springcloudweb.controller;

import com.cloud.springcloudweb.exception.UserNotFoundException;
import com.cloud.springcloudweb.model.User;
import com.cloud.springcloudweb.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/user")
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

    /*@PostMapping
    public Mono<UserDto> saveUser(@Valid @ModelAttribute @RequestBody UserDto userDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return userService.save(userDto);
    }*/
}
