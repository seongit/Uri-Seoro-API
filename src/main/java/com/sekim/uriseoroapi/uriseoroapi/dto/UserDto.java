package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Role;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import lombok.*;

public class UserDto {

    /** 회원 Service 요청(Request) DTO 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private long userNo;
        private String email;
        private String password;
        private String username;
        private String delYN;
        private String createdDate, modifiedDate;
        private Role role;

        /* DTO -> Entity */
        public User toEntity() {
            User user = User.builder()
                    .userNo(userNo)
                    .email(email)
                    .username(username)
                    .password(password)
                    .username(username)
                    .delYN("N")
                    //.role(role.USER)
                    .build();
            return user;
        }

    }

    @Getter
    public static class Response {

        private final Long userNo;
        private final String email;

        private final String password;
        private final String username;

        private final String delYN;
        private final Role role;
        private final String modifiedDate;

        public Response(User user){
            this.userNo = user.getUserNo();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.delYN = user.getDelYN();
            this.modifiedDate = user.getModifiedDate();
        }
    }



}

