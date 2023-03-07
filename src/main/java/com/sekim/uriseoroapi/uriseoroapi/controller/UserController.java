package com.sekim.uriseoroapi.uriseoroapi.controller;

import com.sekim.uriseoroapi.uriseoroapi.config.enums.AdminAuth;
import com.sekim.uriseoroapi.uriseoroapi.config.enums.BaseURLType;
import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import com.sekim.uriseoroapi.uriseoroapi.repository.IssueRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    WebClient webClient = WebClient.builder()
            .baseUrl(BaseURLType.API_SERVER.getUrl())
            .defaultCookie("cookie-name", "cookie-value")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    @Autowired
    private final UserService userService;


    /* 로그인 */
    @PostMapping("/login")
    public UserDto.Response login(@RequestBody UserDto dto){
        return userService.login(dto);
    }

    /* 비밀번호 암호화 백업용 참고 코드
    @PostMapping("login")
    public UserEntity login(@RequestBody UserVo user){
        UserDetails ud = udService.loadUserByUsername(user.getEmail());

        System.out.println(ud);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(ud, user.getPassword());
        authenticationManager.authenticate(authenticationToken);

        System.out.println("authenticationToken.getCredentials()------>"+authenticationToken.getCredentials());
        System.out.println("UserVo ====>" + user.getPassword());
        
        return userService.login(user);
    }
    */


    /* 사용자 등록 */
    @PostMapping("/registerUser")
    public UserDto registerUser(@RequestBody Map<String,Object> param){

        UserDto userDto = UserDto.builder().user(param).build();

        int result = userService.registerUser(userDto);

        UserDto resDto = null;

        if(result > 0){

            resDto =  webClient.post().uri("/users.json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .bodyValue(userDto)     // set body value
                    .retrieve()                 // client message 전송
                    .bodyToMono(UserDto.class)  // body type
                    .block();                   // await

            return resDto;
        }

        return resDto;

    }


    // 사용자 전체 조회 - 페이징 처리
    @GetMapping("/getUsers")
    public JSONObject read(@RequestParam (required = false, defaultValue = "0") int page, @RequestParam (required = false) String searchWord ){

        JSONObject obj = null;

        if(userService.userList().size() > 0 ){
            int offSet = 0 ;

            if(page > 1){
                offSet = (page - 1) * 10;
            }

            obj = webClient.get().uri("/users.json?sort=id&offset=" + offSet + "&limit=10"+"&name="+searchWord)
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey()) // 전체 목록 조회를 위해 Basci - Autho으로 조회
                    .retrieve()                 // client message 전송
                    .bodyToMono(JSONObject.class)  // body type
                    .block();                   // await


            List<Map<String,Object>> obj_users = (List<Map<String, Object>>) obj.get("users");

            for (int i = 0; i < obj_users.size(); i++){

                // 전체 문자열 담기
                String dateTime = obj_users.get(i).get("created_on").toString();

                String date = "";
                date = dateTime.substring(2, dateTime.indexOf("T"));

                String time = "";
                // 10:38 같은 형태로 보이도록 파싱
                time = dateTime.substring(dateTime.indexOf("T")+1,dateTime.indexOf("T")+6);

                String dt = date + " " + time;

                obj_users.get(i).put("created_on",dt);
            }

            obj.put("current_page",page);
            obj.put("per_page", 10);
            obj.put("last_page",obj.get("total_count"));
        }else{
            return (JSONObject) userService.userList();
        }


        return obj;
    }


    // 사용자 상세 조회
    @GetMapping("/getDetailUser/{id}")
    public UserDto getUserDetail(@PathVariable String id){


        UserDto.Response responseUser = userService.getUserDetail(Integer.parseInt(id));

        UserDto userDto = null;

        userDto = webClient.get().uri("/users/" + id + ".json?include=memberships")
                .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                .retrieve()                 // client message 전송
                .bodyToMono(UserDto.class)  // body type
                .block();                   // await

        if(responseUser.getUserNo() ==  (int) userDto.getUser().get("id")) {
            return userDto;
        }else
            return userDto;


    }

    // 사용자 정보 수정
    @PutMapping("/editUser/{id}")
    public String editUser(@PathVariable String id, @RequestBody Map<String,Map<String,Object>> param){


        String resResponseData = "";

        UserDto userDto = UserDto.builder().user(param.get("user")).build();

        int updatedUserId = userService.update(Integer.parseInt(id), userDto);

        if(updatedUserId > 0){

            webClient.put().uri("/users/" + id + ".json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .bodyValue(param)
                    .retrieve()                 // client message 전송
                    .bodyToMono(Map.class)  // body type
                    .block();
            resResponseData = "201 OK";
        }else {
            resResponseData = "api DB 적재 실패";
        }



        return resResponseData;

    }

    // 사용자 삭제
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id){

        int userId = userService.updateStatus(id);

        if(userId > 0){
            webClient.delete().uri("/users/" + id + ".json")
                    .header(HttpHeaders.AUTHORIZATION, AdminAuth.BASIC_BASE_64.getKey())
                    .retrieve()                 // client message 전송
                    .bodyToMono(Map.class)  // body type
                    .block();

            return "200 OK";
        }else{
            System.out.println("ID가 다름" + "DB : " + userId + "레드마인 API : " + id);
        }

        return "";

    }



}
