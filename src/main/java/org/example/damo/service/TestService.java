package org.example.damo.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class TestService {

    private WebClient webClient = WebClient
            .builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .build();

    public Object testSyncApi(){
        String uri = "/posts";


        var response =  webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        return response;


    }
}
