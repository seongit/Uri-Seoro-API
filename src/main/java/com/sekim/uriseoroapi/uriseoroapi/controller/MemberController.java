package com.sekim.uriseoroapi.uriseoroapi.controller;


import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.dto.MemberDto;
import com.sekim.uriseoroapi.uriseoroapi.dto.ProjectDto;
import com.sekim.uriseoroapi.uriseoroapi.service.MemberService;
import com.sekim.uriseoroapi.uriseoroapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    WebClient webClient = WebClient.builder()
            .baseUrl(BaseURLType.API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Autowired
    private final MemberService memberService;

    // 구성원 전체 목록 조회
    @GetMapping("/getAllMembers")
    public JSONObject read(@RequestParam (required = false, defaultValue = "0") int page, String projectId){

        JSONObject obj = null;

        if(memberService.memberList().size()>0){

            int offSet = 0;

            if(page > 1){
                offSet = (page - 1 ) *10;
            }

            obj = webClient.get().uri("/projects/" + projectId + "/memberships.json?&offset=" + offSet + "&limit=10")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 전체 목록 조회를 위해 Basci - Autho으로 조회
                    .retrieve()                 // client message 전송
                    .bodyToMono(JSONObject.class)  // body type
                    .block();                   // await


            obj.put("current_page",page);
            obj.put("per_page", 10);
            obj.put("last_page",obj.get("total_count"));

        }

        return obj;

    }

    // 구성원 상세 조회

    // 구성원 생성
    @PostMapping("/createMember")
    public JSONObject createMember(@RequestBody Map<String,JSONArray> params, @RequestParam (required = false) int projectId){

        MemberDto memberDto = MemberDto.builder().membership(params).projectId(projectId).build();

       int result = memberService.createMember(memberDto, projectId);

        JSONObject res = null;

        // api DB에 정상적으로 insert 되었을 경우
        if(result > 0 ){
            res =  webClient.post().uri("/projects/" + projectId + "/memberships.json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .bodyValue(memberDto)     // set body value
                    .retrieve()                 // client message 전송
                    .bodyToMono(JSONObject.class)  // body type
                    .block();                   // await
        }

        return res;
    }

    // 구성원 수정

    // 구성원 삭제
    @DeleteMapping("/deleteMember/{id}")
    public String deleteMember(@PathVariable int id){

        int memberId = memberService.updateStatus(id);

        if(memberId > 0){
            webClient.delete().uri("/memberships/" + id + ".json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .retrieve()                 // client message 전송
                    .bodyToMono(Map.class)  // body type
                    .block();
            return "200 OK";
        }


        return  "";
    }

}
