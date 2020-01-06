package com.cloud.springcloudweb.handler;

import com.fasterxml.jackson.databind.JsonNode;

import netscape.javascript.JSObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

@Slf4j
@Component
public class TestHandler {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        Mono<String> time = Mono.create(monoSink -> monoSink.success(LocalDateTime.now().format(DATE_FORMAT)));
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(time, String.class);
    }

    public Mono<ServerResponse> test2(ServerRequest serverRequest) {
        Mono<JsonNode> result = WebClient.builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/weather")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", "seoul")
                        .queryParam("APPID", "d367e7f6263cda9a60dfea861a2d0072")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class);


        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, JsonNode.class);
    }

    public Mono<ServerResponse> just(ServerRequest serverRequest) {
        Mono<String> time = Mono.just(Math.random() * 100).map(Objects::toString);
        time.subscribe(System.out::println);
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(time, String.class);
    }

    public Mono<ServerResponse> defer(ServerRequest serverRequest) {
        Mono<String> time = Mono.defer(() -> Mono.just(Math.random() * 100).map(Objects::toString));
        time.subscribe(System.out::println);
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(time, String.class);
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<String> time = Mono.create(monoSink -> monoSink.success(Double.toString(Math.random() * 100)));
        time.subscribe(System.out::println);
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(time, String.class);
    }

    public Mono<String> slowCall(int sec) {
        return Mono.just("data" + sec)
                .delayElement(Duration.ofSeconds(sec))
                .doOnNext(System.out::println)
                .doOnSubscribe(subscription -> System.out.println("call : " + sec));
    }

    public Mono<String> slowCallFlux() {
        return Flux.zip(slowCall(4), slowCall(3), slowCall(2), slowCall(1))
                .reduce("", (a, b) -> a + " : " + b)
                .doOnNext(System.out::println);
    }

    /**
     * zip : 퍼블리셔가 병렬로 호출하고 호출한 순서대로 합친다.
     */
    public Mono<ServerResponse> zip(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(slowCallFlux(), String.class);
    }

    /**
     * merge : 퍼블리셔가 병렬로 호출하고 complete된 순서대로 합친다.
     */
    public Mono<ServerResponse> merge(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(slowCallFlux(), String.class);
    }

    /**
     * concat : 퍼블리셔가 complete되기전까지 다음 아이템을 방출하지 않는다.
     */
    public Mono<ServerResponse> concat(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(slowCallFlux(), String.class);
    }
}
