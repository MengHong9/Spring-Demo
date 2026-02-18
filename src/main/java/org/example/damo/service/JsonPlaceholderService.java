package org.example.damo.service;



import lombok.extern.slf4j.Slf4j;
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



    public List<JsonPlaceholderPostDto> getPosts(){
        String uri = "https://jsonplaceholder.typicode.com/posts";


        List<JsonPlaceholderPostDto> response =(List<JsonPlaceholderPostDto>)  webClientWrapper.getSync(uri , List.class);

        return response;


    }


    public List<JsonPlaceholderCommentDto> getComments(){

        String uri = "https://jsonplaceholder.typicode.com/comments";

        List<JsonPlaceholderCommentDto> response = webClientWrapper.getSync(uri , List.class);

        return response;
    }

}
