package com.sekim.uriseoroapi.uriseoroapi.controller;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.FileUpload;
import com.sekim.uriseoroapi.uriseoroapi.dto.*;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import com.sekim.uriseoroapi.uriseoroapi.service.IssueService;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import javax.websocket.Decoder;
import java.io.*;
import java.net.URI;
import java.util.*;

import org.springframework.core.io.InputStreamResource;

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
            // 완료된 일감까지 확인할 경우 쿼리스트링 ?status_id=closed 붙여주면 되며
            // default는 open된 이슈만 조회됨

            return webClient.get().uri("/issues.json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 전체 목록 조회를 위해 Basci - Autho으로 조회
                    .retrieve()                 // client message 전송
                    .bodyToMono(String.class)  // body type
                    .block();                   // await

        }else{
            return issueService.issueList().toString();
        }
    }

    // 유형 전체 목록 조회
    @GetMapping("/trackers.json")
    public TrackersDto getTrackers(){

        // 일감 전체 조회
        return webClient.get().uri("/trackers.json")
                .retrieve()                 // client message 전송
                .bodyToMono(TrackersDto.class)  // body type
                .block();                   // await

    }

    // 일감 상태 목록 조회
    @GetMapping("/issue_statuses.json")
    public IssueStatusesDto getIssueStatuses(){

        // 일감 전체 조회
        return webClient.get().uri("/issue_statuses.json")
                .retrieve()                 // client message 전송
                .bodyToMono(IssueStatusesDto.class)  // body type
                .block();                   // await

    }

    // 우선 순위 전체 목록 조회
    @GetMapping("/issue_priorities.json")
    public PrioritiesDto getPriorites(){

        // 일감 전체 조회
        return webClient.get().uri("/enumerations/issue_priorities.json")
                .retrieve()                 // client message 전송
                .bodyToMono(PrioritiesDto.class)  // body type
                .block();                   // await

    }


    // 일감 생성
    @PostMapping("/issues.json")
    public IssueDto save(@RequestBody Map<String,Object> param) {

        System.out.println(param);

        // key, value 값을 Map<String,Object> 타입으로 IssutDto 객체에 담음
        IssueDto issueDto = IssueDto.builder()
                            .issue(param).build();

        System.out.println(issueDto.getIssue().get("assigned_to_id"));

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

    /**
     *
     * @param dto
     * @return
     */
    // redmine api 파일 업로드 시에는 token 생성이 필수이며,
    // formdata로 전달 받은 file을 MultipartFile 타입으로 받아 getBytes()로 바이트 배열로 변환함 => 해당 value를 webclient의 bodyValue에 담는다.
    // 클라이언트단에 id와 token return함
    @PostMapping("/uploads.json")
    public UploadTokenDto  createFileToken(@ModelAttribute MultipartFileDto dto){

        MultipartFile multipartFile = dto.getFile();

        // button_01.png와 같이 파일명.확장자 형식으로 출력됨
        // System.out.println(multipartFile.getOriginalFilename());

        Map<String ,Map<String,Object>> resToken = null ;    // await
        UploadTokenDto res = null;

        // 파일명 중복 방지를 위해 UUID 객체 생성
        UUID uuid =UUID.randomUUID();
        String uploadFileName = multipartFile.getOriginalFilename();
        uploadFileName = uuid.toString() + uploadFileName;

        try {

            WebClient webClient = WebClient.builder()
                    .baseUrl(BaseURLType.API_SERVER.getUrl())
                    .defaultCookie("cookie-name", "cookie-value")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .build();

            // 레드마인 api에서 생성되는 responseToken을 클라이언트로 return
            resToken = webClient.post().uri("/uploads.json?filename="+multipartFile.getOriginalFilename())
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 파일 업로드를 위해 Content-Type을 application/octet-stream으로 변경
                    .bodyValue(multipartFile.getBytes()) // multipartFile.getBytes() 호출시 바이트 배열 반환되며, 이를 bodyValue로 담아줌
                    .retrieve()                 // client message 전송
                    .bodyToMono(Map.class)  // body type
                    .block();

            int resId =  (Integer) resToken.get("upload").get("id");
            String token = (String) resToken.get("upload").get("token");

            // uploadTokenDto에 레드마인 api에서 전달 받은 값 담음
            res =  UploadTokenDto.builder()
                    .id(resId)
                    .token(token)
                    .build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {

            // 로컬 디스크로 파일 업로드
            // 테스트를 위해 파일이 업로드가 되는 경로는 /Users/seongeun/Desktop/Test로 설정하였음


            FileOutputStream writer = new FileOutputStream(FileUpload.UPLOAD_PATH.getPath() + uploadFileName);

            // 해당 경로에 파일 생성
            writer.write(multipartFile.getBytes());


        } catch (Exception e) {

            return res;

        }

        return res;
    }


}
