package com.cloud.springcloudweb.router;

import com.cloud.springcloudweb.domain.dto.UserDto;
import com.cloud.springcloudweb.domain.model.User;
import com.cloud.springcloudweb.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Configuration
public class Routes {

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserService userService) {
        return route(GET("api/user/{id}"), request -> ok().body(userService.find(Long.parseLong(request.pathVariable("id"))), User.class))
                .and(route(GET("api/user"), request -> ok().body(userService.findAll(), User.class)))
                .and(route(POST("api/user"), request -> ok().body(userService.save(request.bodyToMono(UserDto.class)), User.class)));
    }

}
