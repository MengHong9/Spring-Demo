package org.example.damo.service;



import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.damo.common.config.ApplicationConfiguration;
import org.example.damo.common.wrapper.WebClientWrapper;
import org.example.damo.dto.external.JsonPlaceholderCommentDto;
import org.example.damo.dto.external.JsonPlaceholderPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JsonPlaceholderService {

    @Autowired
    private WebClientWrapper webClientWrapper;

    @Autowired
    private ApplicationConfiguration appConfig;


    private String BASE_URL;
    private String POSTS_URI;
    private String COMMENTS_URI;


    @PostConstruct
    private void init(){
        this.BASE_URL = appConfig.getJsonPlaceholder().getBaseUrl();
        this.POSTS_URI = appConfig.getJsonPlaceholder().getPostUri();
        this.COMMENTS_URI = appConfig.getJsonPlaceholder().getCommentUri();
    }


    public List<JsonPlaceholderPostDto> getPosts(){
        String url = BASE_URL.concat(POSTS_URI);


        List<JsonPlaceholderPostDto> response =(List<JsonPlaceholderPostDto>)  webClientWrapper.getSync(url , List.class);

        return response;


    }


    public List<JsonPlaceholderCommentDto> getComments(){

        String url = BASE_URL.concat(COMMENTS_URI);

        List<JsonPlaceholderCommentDto> response = webClientWrapper.getSync(url , List.class);

        return response;
    }

}
