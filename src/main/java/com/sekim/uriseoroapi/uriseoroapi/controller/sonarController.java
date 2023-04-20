package com.sekim.uriseoroapi.uriseoroapi.controller;

import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.service.SonarApiService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/sonar")
public class sonarController {

    @Autowired
    private final SonarApiService sonarApiService;

    @GetMapping("/rules/search")
    public JSONObject getAllRulesList(@RequestParam String page){
        System.out.println("page---->"+page);
        return  sonarApiService.getAllRules(page);
    }
}
