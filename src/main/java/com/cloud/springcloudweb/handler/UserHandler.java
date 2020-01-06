package com.cloud.springcloudweb.handler;

import com.cloud.springcloudweb.domain.model.User;
import com.cloud.springcloudweb.repository.UserRepository;
import com.cloud.springcloudweb.validator.UserDtoValidator;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class UserHandler {
    private final UserRepository userRepository;
    private final Validator userDtoValidator;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userDtoValidator = new UserDtoValidator();
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        //Flux<User> users = Flux.fromIterable(userRepository.findAll());
        /*Flux<List<User>> users = Flux.defer(() -> Flux.just(userRepository.findAll()))
                .subscribeOn(Schedulers.elastic());*/

        /*Flux<List<User>> users = Flux.defer(() -> Flux.just(userRepository.findAll()))
                .subscribeOn(Schedulers.elastic());*/


        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(userRepository.findAll(), User.class));
    }

    public String time(){
        System.out.println("time print");
        return "time : " + Math.random() * 100000;
    }

    public Mono<ServerResponse> hot(ServerRequest serverRequest) {
        Mono<String> mono = Mono.just(time());
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mono, String.class);
    }

    public Mono<ServerResponse> cold(ServerRequest serverRequest) {
        Mono<String> mono = Mono.create(stringMonoSink -> stringMonoSink.success(time()));
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mono, String.class);
    }
}
