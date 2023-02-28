package com.sekim.uriseoroapi.uriseoroapi.controller;

import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.dto.ProjectDto;
import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import com.sekim.uriseoroapi.uriseoroapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/project")
public class ProjectController {


    WebClient webClient = WebClient.builder()
            .baseUrl(BaseURLType.API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Autowired
    private final ProjectService projectService;

    // 프로젝트 전체 목록 조회

    @GetMapping("/getAllProjects")
    public JSONObject read(@RequestParam int page){

        JSONObject obj = null;

        if(projectService.projecList().size() > 0){
            int offSet = 0 ;

            if(page > 1){
                offSet = (page - 1) * 10;
            }

            obj = webClient.get().uri("/projects.json?offset=" + offSet + "&limit=10")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 전체 목록 조회를 위해 Basci - Autho으로 조회
                    .retrieve()                 // client message 전송
                    .bodyToMono(JSONObject.class)  // body type
                    .block();                   // await


            List<Map<String,Object>> obj_projects = (List<Map<String, Object>>) obj.get("projects");


            for (int i = 0; i < obj_projects.size(); i++){

                // 전체 문자열 담기
                String dateTime = obj_projects.get(i).get("created_on").toString();

                String date = "";
                date = dateTime.substring(2, dateTime.indexOf("T"));

                String time = "";
                // 10:38 같은 형태로 보이도록 파싱
                time = dateTime.substring(dateTime.indexOf("T")+1,dateTime.indexOf("T")+6);

                String dt = date + " " + time;

                obj_projects.get(i).put("created_on",dt);
            }


            obj.put("current_page",page);
            obj.put("per_page", 10);
            obj.put("last_page",obj.get("total_count"));

        }

        return obj;
    }

    // 프로젝트 상세 조회

    // 프로젝트 생성
    @PostMapping("/createProject")
    public ProjectDto createProject(@RequestBody Map<String,Object> param){

        ProjectDto projectDto = ProjectDto.builder().project(param).build();

        System.out.println(projectDto.toString());

        int result = projectService.createProject(projectDto);

        ProjectDto resDto = null;

        if(result > 0){

            resDto =  webClient.post().uri("/projects.json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .bodyValue(projectDto)     // set body value
                    .retrieve()                 // client message 전송
                    .bodyToMono(ProjectDto.class)  // body type
                    .block();                   // await

            return resDto;
        }

        return null;

    }

    // 프로젝트 수정

    // 프로젝트 삭제
    @DeleteMapping("/deleteProject")
    public String deleteProject(@PathVariable int id){

        projectService.updateStatus(id);

        webClient.delete().uri("/projects/" + id + ".json")
                .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                .retrieve()                 // client message 전송
                .bodyToMono(Map.class)  // body type
                .block();

        return "";
    }

}
