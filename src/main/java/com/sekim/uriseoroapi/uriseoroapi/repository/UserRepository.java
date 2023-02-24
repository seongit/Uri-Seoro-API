package com.sekim.uriseoroapi.uriseoroapi.repository;

import com.sekim.uriseoroapi.uriseoroapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //List <User> findByEmail(@Param("email") String email);
    List <User> findByLogin(@Param("login") String login);

    @Query("SELECT u from User u where u.login = :login and u.mail = :mail")
    List findByLoginAndMail(@Param("login") String login, @Param("mail") String mail);

    //User findByUserNo(@Param("userNo") long userNo);

    //User findByUsername(@Param("writer") String username);

//    @Query("select m from UserEntity m where m.email = :email and m.password = :password")
//    UserEntity findByLogin(@Param("email") String email, @Param("password") String password);

//    @Query("select u from User u where u.email = :email and u.password = :password")
//    User findByLogin(@Param("email") String email, @Param("password") String password);


}
