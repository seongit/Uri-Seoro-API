package com.sekim.uriseoroapi.uriseoroapi.controller;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import com.sekim.uriseoroapi.uriseoroapi.service.IssueService;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @Autowired
    private final IssueService issueService;

    WebClient webClient = WebClient.builder()
            .baseUrl(BaseURLType.API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    // 일감 전체 조회
    @GetMapping("/issues.json")
    public String read(){

        if(issueService.issueList().size() > 0){

            return webClient.get().uri("/issues.json")
                    .retrieve()                 // client message 전송
                    .bodyToMono(String.class)  // body type
                    .block();                   // await

        }else{
            return issueService.issueList().toString();
        }

    }

    // 일감 생성
    @PostMapping("/issues.json")
    public IssueDto save(@RequestBody Map<String,Object> param) {

        System.out.println(param);

        // key, value 값을 Map<String,Object> 타입으로 IssutDto 객체에 담음
        IssueDto issueDto = IssueDto.builder()
                            .issue(param).build();

        int issueNo = issueService.save(issueDto);
        // 정상적으로 API DB insert 시 Redmine API 호출


        if(issueNo > 0 ){
            return  webClient.post().uri("/issues.json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .bodyValue(issueDto)     // set body value
                    .retrieve()                 // client message 전송
                    .bodyToMono(IssueDto.class)  // body type
                    .block();                   // await
        }else{
            return issueDto;
        }

    }
}
