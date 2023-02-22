package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import com.sekim.uriseoroapi.uriseoroapi.repository.IssueRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;


    // 일감 전체 목록 조회
    @Override
    @Transactional
    public List<Issue> issueList() {
       return issueRepository.findAll();
    }


    // 일감 생성
    @Override
    @Transactional
    public int save(IssueDto dto) {
        Issue issue = dto.toEntity();
        System.out.println(issue);
        issueRepository.save(issue);
        return issue.getId();
    }


    // 일감 상세 조회

    @Override
    @Transactional
    public IssueDto.Response getissueDetail(int id) {

        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(

        ));

        return  new IssueDto.Response(issue) ;
    }


    // 일감 수정

    @Override
    @Transactional
    public int update(int id, IssueDto dto) {

        Issue issue = issueRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException());

        int updatedIssueId = issue.update(dto);

        System.out.println("update ===========>" + updatedIssueId);

        return updatedIssueId;

    }

    @Override
    @Transactional
    public int getIssueId(int id) {

        Issue issue = issueRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException());

        return issue.getId();
    }

    @Override
    public int updateCloseDate(int id) {


        return 0;
    }


}
