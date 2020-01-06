package com.cloud.springcloudweb.handler;

import com.cloud.springcloudweb.domain.dto.ResponseDto;
import com.cloud.springcloudweb.exception.FileHandleException;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.File;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class FileHandler {

    public Mono<ServerResponse> upload(ServerRequest serverRequest) {
        Mono<ResponseDto> result = serverRequest.body(BodyExtractors.toMultipartData())
                .map(it -> it.get("files"))
                .flatMapMany(Flux::fromIterable)
                .cast(FilePart.class)
                .flatMap(it -> it.transferTo(new File(String.format("/Users/glenn_mac/temp/%s_%s", System.nanoTime(), it.filename()))))
                .onErrorResume(e -> Mono.error(new FileHandleException()))
                .then(Mono.just(new ResponseDto("success", 201)));


        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, ResponseDto.class);
    }

    public Mono<ServerResponse> download(ServerRequest serverRequest) {
        String filename = serverRequest.pathVariable("filename");
        Mono<File> file = Mono.defer(() -> Mono.just(new File(String.format("/Users/glenn_mac/temp/%s", filename))));

        return ServerResponse
                .ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(file, File.class);
    }
}
