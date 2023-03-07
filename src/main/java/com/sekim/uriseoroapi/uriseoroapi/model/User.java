package com.sekim.uriseoroapi.uriseoroapi.model;

import com.sekim.uriseoroapi.uriseoroapi.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Builder
@Getter
@Entity
@Table(name = "tb_user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userNo")
    private int userNo;

    @Column(unique = true,nullable = false)
    private String login;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String firstname;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String lastname;

    @ColumnDefault("'N'")
    private String delYN;

    @ColumnDefault("'N'")
    private String adminYN;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String createdDate;

    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String modifiedDate;

//    public String getRoleValue() {
//        return this.role.getValue();
//    }

    /* 해당 엔티티를 저장하기 이전에 실행 */
    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createdDate;
    }

    /* 해당 엔티티를 업데이트 하기 이전에 실행*/
    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }


    public int update(UserDto userDto){

        if(userDto.getUser().get("id") != null){
            this.userNo = (Integer) userDto.getUser().get("id");
        }

        if(userDto.getUser().get("login") != null){
            this.login = (String) userDto.getUser().get("login");
        }

        if(userDto.getUser().get("firstname") != null){
            this.firstname = (String) userDto.getUser().get("firstname");
        }

        if(userDto.getUser().get("lastname") != null){
            this.lastname = (String) userDto.getUser().get("lastname");
        }

        if(userDto.getUser().get("mail") != null){
            this.mail = (String) userDto.getUser().get("mail");
        }


        if(userDto.getUser().get("admin") != null){

            if((boolean) userDto.getUser().get("admin")){
                this.adminYN = "Y";
            }else{
                this.adminYN = "N";
            }

        }


        return this.userNo;
    }


}
