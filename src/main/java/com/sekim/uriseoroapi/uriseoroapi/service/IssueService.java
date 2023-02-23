package com.sekim.uriseoroapi.uriseoroapi.service;

import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    // 일감 전체 목록 조회
    List<Issue> issueList();

    // 일감 생성
    int save(IssueDto dto);

    // 일감 상세 조회
    IssueDto.Response getissueDetail(int id);


    // 일감 업데이트
    int update(int id, IssueDto dto);

    int getIssueId(int id);


    // 일감 삭제
    int updateStatus(int id);

}
