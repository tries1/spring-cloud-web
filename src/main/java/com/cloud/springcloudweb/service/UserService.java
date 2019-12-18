package com.cloud.springcloudweb.service;

import com.cloud.springcloudweb.model.User;
import com.cloud.springcloudweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    public Flux<User> findAll(){
        return Flux.fromIterable(userRepository.findAll());
    }

    public Mono<User> find(Long id){
        return Mono.just(userRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
