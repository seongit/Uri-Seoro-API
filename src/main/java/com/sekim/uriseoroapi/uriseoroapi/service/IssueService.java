package com.sekim.uriseoroapi.uriseoroapi.service;

import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;

import java.util.List;

public interface IssueService {


    // 일감 생성
    Long save(IssueDto.Request dto);

    // 일감 전체 목록 조회
    List<Issue> issueList();

}
