package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.service.SonarApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SonarApiServiceImpl implements SonarApiService {

    //DataBufferLimitException 방지를 위해 buffer 크기 변경
    ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
            .build();

    WebClient webClient = WebClient.builder()
            .exchangeStrategies(exchangeStrategies) // set exchange strategies
            .baseUrl(BaseURLType.SONAR_API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    public JSONObject getAllRules(String page) {

        JSONObject obj = new JSONObject();

        String reqPage = "";
        // todo defaultValue is not working
        if("".equals(page) || page == null){
//            System.out.println("여기 들어옴?");
            reqPage = "1";
        }else{
            reqPage = page;
        }

        obj = webClient.get().uri("/api/rules/search"+"?p="+reqPage + "&facets=languages,types,tags,repositories"+"&s=name")
                .header(HttpHeaders.AUTHORIZATION, AdminAuth.SONAR_BASIC_BASE_64.getKey())
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

        obj.put("current_page",page);
        obj.put("per_page", 100);
        obj.put("last_page",obj.get("total"));

        return obj;
    }

    public JSONObject getAllSonarLanguagesList(){

        JSONObject obj = new JSONObject();
        obj =webClient.get().uri("/api/languages/list")
                .header(HttpHeaders.AUTHORIZATION, AdminAuth.SONAR_BASIC_BASE_64.getKey())
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

        return obj;
    }


}
