package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.MemberDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Member;
import com.sekim.uriseoroapi.uriseoroapi.repository.MemberRepository;
import com.sekim.uriseoroapi.uriseoroapi.repository.ProjectRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;


    // 구성원 전체 목록 조회
    @Override
    public List<Member> memberList() {
        return memberRepository.findAll();
    }

    // 구성원 상세 조회


    // 구성원 생성
    @Override
    public int createMember(MemberDto dto, int projectId) {

        int result = 0;

        List <Member> memberEntity = dto.toEntity();

        // 구성원 ID 별로 DB에 insert됨
        for(int i=0; i<memberEntity.size(); i++){
            System.out.println("memberEntity.get(i)" + memberEntity.get(i).getUserId());
            memberRepository.save(memberEntity.get(i));
            result = memberEntity.size();
        }

        return result;
    }


    // 구성원 수정
    @Override
    public int update(int id, MemberDto dto) {
        return 0;
    }


    // 구성원 삭제
    @Override
    public int updateStatus(int id) {
        return 0;
    }










}
