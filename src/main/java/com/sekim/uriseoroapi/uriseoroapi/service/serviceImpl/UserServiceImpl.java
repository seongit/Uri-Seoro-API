package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
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

    //@Autowired
    final UserRepository userRepository;

    @Override
    @Transactional
    public int registerUser(UserDto dto) {

        int result = 0;

        User resDto = dto.toEntity();

        System.out.println("resDto=====>" + resDto.toString());

        String login = resDto.getLogin();
        String mail = resDto.getMail();

        if(validateDuplicateUser(login,mail)){
            userRepository.save(resDto);
            result = resDto.getUserNo();
        };

        return result;
    }

    // 사용자 전체 목록 조회
    @Override
    @Transactional
    public List<User> userList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserDto.Response getUserDetail(int userNo) {

        User user = userRepository.findById(userNo).orElseThrow(()-> new IllegalArgumentException());

        return new UserDto.Response(user);
    }

    @Override
    @Transactional
    public int update(int id, UserDto dto) {
        
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        
        int updatedUserId = user.update(dto);

        return updatedUserId;
    }


    // 추후 구현 예정
    //    @Autowired
    //    AuthenticationManager authenticationManager;

    // 암호화 알고리즘 확인 필요
    /*
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    */


    /*
    /* 회원가입
    @Override
    public User signUp(UserDto_BU.Request dto) {
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
    } */


    // 중복회원 검증

    private boolean validateDuplicateUser(String login, String mail){
        boolean answer = true;

        List user = userRepository.findByLoginAndMail(login, mail);

        if(!user.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("회원가입 실패");
        }


        return answer;
    }



    /* 로그인
    @Override
    public User login(UserDto_BU.Request dto) {
        User user = userRepository.findByLogin(dto.getEmail(), dto.getPassword());
        return user;
    } */


    // 테스트
    /*

    @Override
    public UserDto_BU.Response findById(long userNo) {
        User user = userRepository.findById(userNo).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. : " + userNo));
        return new UserDto_BU.Response(user);
    }
    */



}
