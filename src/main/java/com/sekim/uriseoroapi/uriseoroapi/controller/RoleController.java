package com.sekim.uriseoroapi.uriseoroapi.controller;


import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.dto.IssueStatusesDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {


    WebClient webClient = WebClient.builder()
            .baseUrl(BaseURLType.API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    // 역할 전체 목록 조회
    @GetMapping("/getRoles")
    public JSONObject getRoles(){

        // 일감 전체 조회
        return webClient.get().uri("/roles.json")
                .retrieve()                 // client message 전송
                .bodyToMono(JSONObject.class)  // body type
                .block();                   // await

    }

}
