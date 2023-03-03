package com.sekim.uriseoroapi.uriseoroapi.repository;

import com.sekim.uriseoroapi.uriseoroapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Integer> {


}
