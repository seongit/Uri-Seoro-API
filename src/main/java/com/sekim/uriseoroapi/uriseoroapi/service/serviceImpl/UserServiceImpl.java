package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import com.sekim.uriseoroapi.uriseoroapi.repository.UserRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    final UserRepository userRepository;

    // 추후 구현 예정
    //    @Autowired
    //    AuthenticationManager authenticationManager;

    // 암호화 알고리즘 확인 필요
    /*
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    */


    /* 회원가입 */
    @Override
    public User signUp(UserDto.Request dto) {
        // 비밀번호 암호화 구현 예정
//        String encPassword = passwordEncoder.encode(dto.getPassword());
//        System.out.println("1encPassword====>"+encPassword);

        User user  = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .build();

        validateDuplicateUser(user.getEmail());

        return userRepository.save(user);
    }


    // 중복회원 검증
    private void validateDuplicateUser(String email){
        boolean answer = false;
        // EXCEPTION
        List<User> user = userRepository.findByEmail(email);
        if(!user.isEmpty()){
            new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    /* 로그인 */
    @Override
    public User login(UserDto.Request dto) {
        User user = userRepository.findByLogin(dto.getEmail(), dto.getPassword());
        return user;
    }


    // 테스트
    @Override
    public UserDto.Response findById(long userNo) {
        User user = userRepository.findById(userNo).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. : " + userNo));
        return new UserDto.Response(user);
    }




}
