package com.sekim.uriseoroapi.uriseoroapi.service;

import com.sekim.uriseoroapi.uriseoroapi.dto.MemberDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Member;

import java.util.List;

public interface MemberService {


    // 구성원 전체 목록 조회
    List <Member> memberList();

    // 구성원 상세 조회
//    MemberDto.Response getMemberDetail(int id);

    // 구성원 생성
    int createMember(MemberDto dto, int projectId);

    // 구성원 수정
    int update(int id, MemberDto dto);

    // 구성원 삭제
    int updateStatus(int id);


}
