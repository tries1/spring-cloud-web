package com.cloud.springcloudweb.handler;

import com.cloud.springcloudweb.domain.model.User;
import com.cloud.springcloudweb.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;

import netscape.javascript.JSObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple4;

@Slf4j
@Component
public class TestHandler {
    private final UserRepository userRepository;
    private static AtomicLong atomicLong = new AtomicLong();

    public TestHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Mono<String> slowCall(int sec) {
        return Mono.just("slow " + sec)
                .delayElement(Duration.ofSeconds(sec))
                .doOnNext(log::info)
                .doOnSubscribe(subscription -> log.info("call : {}", sec));
    }

    public User findUser() {
        System.out.println("call find user!!" + atomicLong.incrementAndGet());
        return userRepository.findTopByOrderByIdDesc();
    }

    public Mono<ServerResponse> eventStream(ServerRequest serverRequest) {

        Flux<String> eventStream = Flux
                .interval(Duration.ofSeconds(1))
                .flatMap(s -> Mono.just(findUser()))
                .map(User::getName)
                .distinct()
                .map(name -> String.format(" Event emit!!, %s, name=%s", LocalDateTime.now().format(DATE_FORMAT), name))
                .doOnNext(log::info);

        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventStream, String.class);
    }

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        Mono<String> time = Mono.create(monoSink -> monoSink.success(LocalDateTime.now().format(DATE_FORMAT)));
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(time, String.class);
    }

    public Mono<ServerResponse> webclient(ServerRequest serverRequest) {
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

    public String time() {
        System.out.println("time print");
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "time : " + Math.random() * 100000;
    }

    /**
     * just는 Hot Publisher로 구독(subscribe)가 되기전부터 데이터를 방출하며 방출된 값을 캡쳐해두었다가 이후 구독자에 전달한다.
     *
     * @param serverRequest
     * @return
     */
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

    /**
     * create는 Cold Publisher로 구독(subscribe)이 되어진 이후부터 데이터를 방출하며 매번 데이터를 새로 수행하여 방출한다.
     *
     * @param serverRequest
     * @return
     */
    AtomicInteger ai = new AtomicInteger(1);
    public Mono<ServerResponse> cold(ServerRequest serverRequest) {
        Mono<String> mono = Mono.create(stringMonoSink -> {
            System.out.println("call : " + ai.getAndIncrement());
            stringMonoSink.success(time());
        });

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mono, String.class);
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

    public Mono<String> slowCallFlux() {
        return Flux.zip(slowCall(4), slowCall(3), slowCall(2), slowCall(1))
                .reduce("", (a, b) -> a + " : " + b)
                .doOnNext(log::info);
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

    public String slowCallForMap(int sec) {
        try {
            log.info("call : {}", sec);
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return sec + " ok";
    }

    /**
     * flatmap : map과는 다르게 리턴값을 Publisher로 리턴하게되며 각각의 publisher가 생성되고 합쳐져서 리턴된다.
     * 새로운 Publisher가 생성되기때문에 비동기로 한번에 호출할떄 사용할수있음
     */
    public Mono<ServerResponse> flatmap(ServerRequest serverRequest) {
        Flux.just(3, 6, 7, 10, 4, 5, 8, 2, 1, 9)
                //.flatMap(it -> Mono.just(it).delayElement(Duration.ofSeconds(it)).log())
                //.flatMap(it -> Mono.fromCallable(() -> slowCallForMap(it)).subscribeOn(Schedulers.elastic()))//블록킹 작업을 별도의 elastic thread를 사용하여 처리
                .flatMap(it -> Mono.defer(() -> Mono.just(slowCallForMap(it))).subscribeOn(Schedulers.elastic()))

                /*.flatMap(it -> Mono.just(it)
                        .doOnNext(i -> slowCallForMap(i))
                        .subscribeOn(Schedulers.elastic()))*/
                //.map(it -> slowCallForMap(it))
                /*.flatMap(it -> Flux.just(it)
                        .delayElements(Duration.ofSeconds(it))
                        .doOnSubscribe(System.out::println))
                .doOnNext(System.out::println)*/
                .log()
                .subscribe();

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(1), Integer.class);
    }

    public Mono<ServerResponse> dummyUserCreate(ServerRequest serverRequest) {
        /*Flux.interval(Duration.ofMillis(10))
                .flatMap(it -> Mono.just(userRepository.save(User.builder().name("dummy" + it).build())))
                .log()
                .subscribeOn(Schedulers.elastic())
                .subscribe();*/

        Mono<String> name = Mono.defer(() -> Mono.just(userRepository.save(User.builder().name("dummy").build()))
                .map(User::getName)
                .log()
                .subscribeOn(Schedulers.elastic())
        );

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(name, String.class);
    }
}
