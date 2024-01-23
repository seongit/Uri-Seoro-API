package com.sekim.uriseoroapi.uriseoroapi.controller;

import com.sekim.uriseoroapi.uriseoroapi.service.SonarApiService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/sonar")
public class SonarController {

    @Autowired
    private SonarApiService sonarApiService;

    /**
     * Rules List 전체 목록 조회 (페이징 처리 완료)
     * @param page
     * @return
     */
    @GetMapping("/rules/search")
    public JSONObject getAllRulesList(@RequestParam (required =false) String page ){
        return  sonarApiService.getAllRules(page);
    }

    /**
     * Languages List 전체 목록 조회
     * @return
     */
    @CrossOrigin("*")
    @GetMapping("/languages/list")
    public JSONObject getAllSonarLanguagesList(){
        return sonarApiService.getAllSonarLanguagesList();
    }

}
