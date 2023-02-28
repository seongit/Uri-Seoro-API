package com.sekim.uriseoroapi.uriseoroapi.service;

import com.sekim.uriseoroapi.uriseoroapi.dto.ProjectDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Project;

import java.util.List;

public interface ProjectService {
    // 프로젝트 전체 목록 조회

    List<Project> projecList();

    // 프로젝트 상세 조회

    // 프로젝트 생성
    int createProject(ProjectDto dto);

    // 프로젝트 수정

    // 프로젝트 삭제
    int updateStatus(int id);
}
