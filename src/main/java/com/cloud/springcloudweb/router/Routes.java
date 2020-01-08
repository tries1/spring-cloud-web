package com.cloud.springcloudweb.router;

import com.cloud.springcloudweb.domain.dto.UserDto;
import com.cloud.springcloudweb.domain.model.User;
import com.cloud.springcloudweb.handler.FileHandler;
import com.cloud.springcloudweb.handler.TestHandler;
import com.cloud.springcloudweb.handler.UserHandler;
import com.cloud.springcloudweb.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Configuration
public class Routes {

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserService userService, UserHandler userHandler) {
        return route(GET("api/user/{id}"), request -> ok().body(userService.find(Long.parseLong(request.pathVariable("id"))), User.class))
                .andRoute(GET("api/user"), request -> ok().body(userService.findAll(), User.class))
                .andRoute(POST("api/user"), request -> ok().body(userService.save(request.bodyToMono(UserDto.class)), User.class));
        //.andRoute(POST("api/user2").and(accept(MediaType.APPLICATION_JSON)), userService::save2);
    }

    @Bean
    RouterFunction<ServerResponse> testRoutes(TestHandler testHandler) {
        return route().path("api/test", () ->
                route(GET("/event"), testHandler::eventStream)
                .andRoute(GET("test2").and(accept(MediaType.APPLICATION_JSON)), testHandler::test)
                .andRoute(GET("webclient").and(accept(MediaType.APPLICATION_JSON)), testHandler::webclient)
                .andRoute(GET("hot").and(accept(MediaType.APPLICATION_JSON)), testHandler::hot)
                .andRoute(GET("cold").and(accept(MediaType.APPLICATION_JSON)), testHandler::cold)
                .andRoute(GET("just").and(accept(MediaType.APPLICATION_JSON)), testHandler::just)
                .andRoute(GET("defer").and(accept(MediaType.APPLICATION_JSON)), testHandler::defer)
                .andRoute(GET("create").and(accept(MediaType.APPLICATION_JSON)), testHandler::create)
                .andRoute(GET("zip").and(accept(MediaType.APPLICATION_JSON)), testHandler::zip)
                .andRoute(GET("merge").and(accept(MediaType.APPLICATION_JSON)), testHandler::merge)
                .andRoute(GET("concat").and(accept(MediaType.APPLICATION_JSON)), testHandler::concat)
                .andRoute(GET("flatmap").and(accept(MediaType.APPLICATION_JSON)), testHandler::flatmap)
                .andRoute(GET("dummyUserCreate").and(accept(MediaType.APPLICATION_JSON)), testHandler::dummyUserCreate)

        ).build();
    }

    @Bean
    RouterFunction<ServerResponse> fileRoutes(FileHandler fileHandler) {
        return route(POST("api/file").and(accept(MediaType.MULTIPART_FORM_DATA)), fileHandler::upload)
                .andRoute(GET("api/file/{filename}"), fileHandler::download);
    }
}
