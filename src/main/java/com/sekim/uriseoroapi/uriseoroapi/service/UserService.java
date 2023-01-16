package com.sekim.uriseoroapi.uriseoroapi.service;

import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import com.sekim.uriseoroapi.uriseoroapi.model.User;

public interface UserService {

     User signUp(UserDto.Request dto);

     //UserEntity login(UserVo user);

     User login(UserDto.Request dto);


     UserDto.Response findById(long userNo);

}
