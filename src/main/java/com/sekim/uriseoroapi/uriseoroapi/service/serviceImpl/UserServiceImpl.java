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
    public UserDto.Response login(UserDto dto) {
        String login =  (String) dto.getUser().get("login");
        String password =  (String) dto.getUser().get("password");

        User user = userRepository.findByLoginAndPassword(login,password);

        if(user == null){
            throw new IllegalArgumentException("ID 또는 비밀번호를 확인해주세요");
        }

        return new UserDto.Response(user);
    }


    // 사용자 등록
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


    // 사용자 상세 정보 조회
    @Override
    @Transactional
    public UserDto.Response getUserDetail(int userNo) {

        User user = userRepository.findById(userNo).orElseThrow(()-> new IllegalArgumentException());

        return new UserDto.Response(user);
    }


    // 사용자 정보 수정
    @Override
    @Transactional
    public int update(int id, UserDto dto) {
        
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());


        // 변경하고자 하는 Login ID
        String toChangeLogin = (String) dto.getUser().get("login") ;
        // 기존 Login ID
        String originLogin = user.getLogin();

        // 변경하고자 하는 Mail
        String toChangeMail = (String) dto.getUser().get("mail") ;
        // 기존 Mail
        String originMail = user.getMail();

        int updatedUserId = 0;

        // id만 변경 되었을 경우
        if(!toChangeLogin.equals(originLogin)){
            if(validateDuplicateLogin(toChangeLogin)){
                updatedUserId = user.update(dto);
            };
        }else if(!toChangeMail.equals(originMail)) {
            // 메일만 변경 되었을 경우
            if(validateDuplicateMail(toChangeMail)){
                updatedUserId = user.update(dto);
            }
        }else{
            updatedUserId = user.update(dto);
        }

        return updatedUserId;
    }

    @Override
    public int updateStatus(int id) {

        int result = userRepository.updateStatus(id);

        return result;
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


    // 중복회원 검증 - 사용자 생성 시
    private boolean validateDuplicateUser(String login, String mail){
        boolean answer = true;

        List user = userRepository.findByLoginAndMail(login, mail);


        if(!user.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("[사용자 등록 실패] ID 또는 이메일을 확인해주세요.");
        }

        return answer;
    }

    // 중복 ID 검증 - 사용자 정보 수정 시
    private boolean validateDuplicateLogin(String login){
        boolean answer = true;

        List user = userRepository.findByLogin(login);

        if(!user.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("[사용자 수정 실패] ID 또는 이메일을 확인해주세요.");
        }

        return answer;
    }


    // 중복 Email 검증 - 사용자 정보 수정 시
    private boolean validateDuplicateMail(String mail){
        boolean answer = true;

        List user = userRepository.findByMail(mail);

        if(!user.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("[사용자 수정 실패] ID 또는 이메일을 확인해주세요.");
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
