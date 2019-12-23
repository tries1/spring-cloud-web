package com.cloud.springcloudweb.service;

import com.cloud.springcloudweb.dto.UserDto;
import com.cloud.springcloudweb.exception.UserNotFoundException;
import com.cloud.springcloudweb.model.User;
import com.cloud.springcloudweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    public Flux<User> findAll() {
        return Flux.fromIterable(userRepository.findAll())
                .subscribeOn(Schedulers.elastic());
    }

    public Mono<User> find(Long id) {
        return Mono.just(userRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .onErrorResume(e -> Mono.error(new UserNotFoundException(id)))
                .subscribeOn(Schedulers.elastic());
    }

    public Mono<UserDto> save(Mono<UserDto> userDto) {
        return userDto
                .doOnNext(it -> userRepository.save(User.builder().name(it.getName()).build()))
                .subscribeOn(Schedulers.elastic());
    }
}
