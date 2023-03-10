package com.sekim.uriseoroapi.uriseoroapi.controller;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.FileUpload;
import com.sekim.uriseoroapi.uriseoroapi.dto.*;
import com.sekim.uriseoroapi.uriseoroapi.service.IssueService;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private final IssueService issueService;

    WebClient webClient = WebClient.builder()
            .baseUrl(BaseURLType.API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    // 일감 전체 조회 - 기존 소스 백업
//    @GetMapping("/getIssues/offset={offsetNo}")
//    public String read(@PathVariable String offsetNo){
//
//        System.out.println(offsetNo);
//
//
//        if(issueService.issueList().size() > 0){
//            // 완료된 일감까지 확인할 경우 쿼리스트링 ?status_id=closed 붙여주면 되며
//            // default는 open된 이슈만 조회됨
//
//            return webClient.get().uri("/issues.json?offset=" + offsetNo + "&limit=10")
//                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 전체 목록 조회를 위해 Basci - Autho으로 조회
//                    .retrieve()                 // client message 전송
//                    .bodyToMono(String.class)  // body type
//                    .block();                   // await
//
//        }else{
//            return issueService.issueList().toString();
//        }
//    }

    /**
     * @author se, js
     * @param page
     * @return
     */
    // 일감 전체 조회
    @GetMapping("/getIssues/test")
    public JSONObject read(@RequestParam int page
            ,@RequestParam (required = false) int assigned_to_id
            ,@RequestParam (required = false) String status_id
            ,@RequestParam (required = false) String project_id){

        JSONObject obj = null;

        if(issueService.issueList().size() > 0){
            // 완료된 일감까지 확인할 경우 쿼리스트링 ?status_id=closed 붙여주면 되며
            // default는 open된 이슈만 조회됨

            int offSet = 0 ;

            if(page > 1){
                offSet = (page - 1) * 10;
            }

            String assignedId = "";
            if( assigned_to_id > 1) {
                assignedId = "&assigned_to_id=" + assigned_to_id;
            }

            String statusId = "";
            if ( status_id != "") {
                statusId = "&status_id=" + status_id;

                System.out.println("statusId" + statusId);
            }

            String projectId = "";
            if (project_id != ""){
                projectId = "&project_id=" + project_id;
                System.out.println("statusId" + projectId);
            }


            obj = webClient.get().uri("/issues.json?offset=" + offSet + "&limit=10" + assignedId + statusId + projectId)
                            .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 전체 목록 조회를 위해 Basci - Autho으로 조회
                            .retrieve()                 // client message 전송
                            .bodyToMono(JSONObject.class)  // body type
                            .block();                   // await


            System.out.println(obj.get("issues"));

            List<Map<String,Object>> obj_issue = (List<Map<String, Object>>) obj.get("issues");

            for (int i = 0; i < obj_issue.size(); i++){

                // 전체 문자열 담기
                String dateTime = obj_issue.get(i).get("updated_on").toString();

                String date = "";
                date = dateTime.substring(2, dateTime.indexOf("T"));

                String time = "";
                // 10:38 같은 형태로 보이도록 파싱
                time = dateTime.substring(dateTime.indexOf("T")+1,dateTime.indexOf("T")+6);

                String dt = date + " " + time;

                obj_issue.get(i).put("updated_on",dt);
            }

            obj.put("current_page",page);
            obj.put("per_page", 10);
            obj.put("last_page",obj.get("total_count"));


        }else{
            return (JSONObject) issueService.issueList();
        }

        return obj;
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


    // 일감 상세 조회
    @GetMapping("/{id}")
    public IssueDto getIssueDetail(@PathVariable String id){

        System.out.println(id);
        // api 서버 - 조회 다녀옴
        IssueDto.Response responseIssue = issueService.getissueDetail(Integer.parseInt(id));

        IssueDto issueDto = null;

        try {
            // 첨부파일 포함하여 조회
            issueDto = webClient.get().uri("/issues/" + id + ".json?include=attachments")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .retrieve()                 // client message 전송
                    .bodyToMono(IssueDto.class)  // body type
                    .block();                   // await

            int result = (int) issueDto.getIssue().get("id");

            if (responseIssue.getId() == result){
                return issueDto;

            }else {
                System.out.println("ID가 다름" + "DB : " + responseIssue.getId() + "레드마인 API : " + result);
            }

        }catch (Exception e){
            // 레드마인 상 없는 게시글일 경우 404 Not Found 발생
            System.out.println(e.getMessage());

        }

        return issueDto;

    }


    // 일감 수정
    @PutMapping("/{id}")
    public String editIssue(@PathVariable int id, @RequestBody Map<String,Map<String,Object>> param){

        String resResponseData = "";

        // api 서버에도 전달 받은 데이터 업데이트

//        try{

        System.out.println("param====>"+param);

            IssueDto issueDto = IssueDto.builder().issue(param.get("issue")).build();

            System.out.println("test=====> assigned_id "+issueDto.getIssue().get("assigned_to_id"));

            int updatedIssueId = issueService.update(id,issueDto);

            if(updatedIssueId > 0 ){

                webClient.put().uri("/issues/" + id + ".json")
                        .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                        .bodyValue(param)
                        .retrieve()                 // client message 전송
                        .bodyToMono(Map.class)  // body type
                        .block();

                resResponseData = "201 OK";
            }else {
                resResponseData = "api DB 적재 실패";
            }

//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            resResponseData = e.getMessage();
//            System.out.println(e);
//        }


        return  resResponseData;

    }

    // 일감 첨부파일 삭제
    @DeleteMapping("/attachment/{id}")
    public String deleteAttachment(@PathVariable int id){

        webClient.delete().uri("/attachments/" + id + ".json")
                .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                .retrieve()                 // client message 전송
                .bodyToMono(Map.class)  // body type
                .block();

        return "200 OK";

    }

    // 일감 삭제
    @DeleteMapping("/deleteIssue/{id}")
    public String deleteIssue(@PathVariable int id){

        int issueId = issueService.updateStatus(id);

        System.out.println("issueId =====> " + issueId);

        if (issueId > 0){
            webClient.delete().uri("/issues/" + id + ".json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .retrieve()                 // client message 전송
                    .bodyToMono(Map.class)  // body type
                    .block();

           return "200 OK";

        }else {
            System.out.println("ID가 다름" + "DB : " + issueId + "레드마인 API : " + id);
        }

        return "";

    }


}
