package org.example.damo.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class TestService {

    private WebClient webClient = WebClient
            .builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .build();

    public Object testSyncApi(){
        String uri = "/posts";


        Object response =  webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofMillis(10000))
                .block();

        return response;


    }


    public Object testASyncApi(){
        String uri = "/posts";

        log.info("Test : Before API request");

        Mono response =  webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Object.class);

        response.subscribe(res -> {
            log.info("Test : Getting response");
        });



        log.info("Test : After API request");

        return response;


    }
}
