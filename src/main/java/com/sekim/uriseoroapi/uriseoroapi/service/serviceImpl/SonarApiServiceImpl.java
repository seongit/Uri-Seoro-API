package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.config.enums.APIConstants;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.service.SonarApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SonarApiServiceImpl implements SonarApiService {

    @Autowired
    RestTemplate restTemplate;

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



    /**
     * 소나큐브 서버 헬스체크
     * @return
     */
    public boolean isUpSonar(){
        boolean isServerUp = false;
        ResponseEntity<Map> responseEntity = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(sonarId, sonarPw);
        responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            String status = (String) responseEntity.getBody().get("status");
            if(status.equalsIgnoreCase("UP")){
                isServerUp = true;
            }
        }
        return isServerUp;
    }

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


    /**
     * 룰셋 정보 상세 조회 (기록용)
     */
//    public String selectRuleSetDtail(){
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(sonarId, sonarPw));
//
//        List<RuleSetDtailInfoOut> list = new ArrayList<>();
//
//        // 룰 점검우선순위별로 그룹핑
//        Map<String, List<RuleSetDtailInfoOut>> resultList = list.stream()
//                .collect(Collectors.groupingBy(RuleSetDtailInfoOut::getSeverity));
//
//        // 점검우선순위별로 정렬
//        List<APIConstants.SEVERITY> severityList = Arrays.asList(APIConstants.SEVERITY.values());
//        //List<String> severityList = Arrays.asList("BLOCKER","CRITICAL","MAJOR","MINOR");
//
//        Map<String,List<RuleSetDtailInfoOut>> soredList = new LinkedHashMap<>();
//
//        for(APIConstants.SEVERITY severity : severityList){
//            String severityString = severity.name();
//            if(resultList.containsKey(severityString)){
//                soredList.put(severityString,resultList.get(severityString));
//            }
//        }
//        return "soredList 반환";
//    }
}
