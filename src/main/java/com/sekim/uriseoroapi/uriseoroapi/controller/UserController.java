package com.sekim.uriseoroapi.uriseoroapi.controller;

import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import com.sekim.uriseoroapi.uriseoroapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    @Autowired
    private final UserService userService;

    /* 회원가입 */
    @PostMapping("/signup")
    public User signUp(@RequestBody UserDto.Request dto){
        return userService.signUp(dto);
    }


    /* 로그인 */
    @PostMapping("/login")
    public User login(@RequestBody UserDto.Request dto){
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


}
