package com.sekim.uriseoroapi.uriseoroapi.dto;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import lombok.*;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    Map<String,Object> user;


    /* Dto -> Entity */
    public User toEntity(){

        String login = "";

        String mail = "";

        String password = "";

        String firstname = "";

        String lastname = "";

        String adminYN = "N";

        String delYN = "N";

        login = (String) user.get("login");
        mail = (String) user.get("mail");
        password = (String) user.get("password");
        firstname = (String) user.get("firstname");
        lastname = (String) user.get("lastname");

        if((boolean) user.get("admin") != false){
            adminYN = "Y";
        }

        User res = User.builder()
                .login(login)
                .mail(mail)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .delYN(delYN)
                .adminYN(adminYN)
                .build();

        return res;
    }


    @Getter
    public static class Response{

        private final int userNo;

        private final String login;

        private final String mail;

        private final String password;

        private final String firstname;

        private final String lastname;

        private final String delYN;

        private final String adminYN;


        public Response(User user){
            this.userNo = user.getUserNo();
            this.login = user.getLogin();
            this.mail = user.getMail();
            this.password = user.getPassword();
            this.firstname = user.getFirstname();
            this.lastname = user.getLastname();
            this.delYN = user.getDelYN();
            this.adminYN = user.getAdminYN();

        }
    }



}
