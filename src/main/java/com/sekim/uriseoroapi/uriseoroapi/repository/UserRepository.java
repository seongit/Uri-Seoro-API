package com.sekim.uriseoroapi.uriseoroapi.repository;

import com.sekim.uriseoroapi.uriseoroapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    // List <User> findByEmail(@Param("email") String email);
    List <User> findByLogin(@Param("login") String login);

    @Query("SELECT u from User u where u.login = :login and u.password = :password and u.delYN = 'N' ")
    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Modifying
    @Query("SELECT u from User u where u.mail = :mail")
    List <User> findByMail(@Param("mail") String mail);

    // 사용자 등록을 위해 기존 등록된 사용자 계정 조회 (login or mail)
    @Modifying
    @Query("SELECT u from User u where u.login = :login or u.mail = :mail")
    List findByLoginAndMail(@Param("login") String login, @Param("mail") String mail);

    @Modifying
    @Query("update User u set u.delYN = 'Y' where u.userNo = :id and u.delYN = 'N'")
    int updateStatus(int id);

    //User findByUserNo(@Param("userNo") long userNo);

    //User findByUsername(@Param("writer") String username);

//    @Query("select m from UserEntity m where m.email = :email and m.password = :password")
//    UserEntity findByLogin(@Param("email") String email, @Param("password") String password);

//    @Query("select u from User u where u.email = :email and u.password = :password")
//    User findByLogin(@Param("email") String email, @Param("password") String password);


}
