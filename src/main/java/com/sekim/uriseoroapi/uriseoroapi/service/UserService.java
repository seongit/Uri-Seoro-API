package com.sekim.uriseoroapi.uriseoroapi.service;

import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import com.sekim.uriseoroapi.uriseoroapi.model.User;

import java.util.List;

public interface UserService {

//     User signUp(UserDto.Request dto);
//
//     //UserEntity login(UserVo user);
//
//     User login(UserDto_BU.Request dto);
//
//
//     UserDto_BU.Response findById(long userNo);

    // 신규 사용자 등록
    int registerUser(UserDto dto);

    // 사용자 전체 목록 조회
    List <User> userList();

    // 사용자 상세 조회
    UserDto.Response getUserDetail(int userNo);

    // 사용자 정보 변경
    int update(int id, UserDto dto);

}
