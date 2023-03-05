package com.sekim.uriseoroapi.uriseoroapi.repository;

import com.sekim.uriseoroapi.uriseoroapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    @Modifying
    @Query("update Member m set m.delYN = 'Y' where m.id = :id and m.delYN = 'N'")
    int updateStatus(int id);

}
