package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.ProjectDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Project;
import com.sekim.uriseoroapi.uriseoroapi.repository.ProjectRepository;
import com.sekim.uriseoroapi.uriseoroapi.repository.UserRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    final ProjectRepository projectRepository;


    // 프로젝트 전체 목록 조회
    @Override
    public List<Project> projecList() {
        return projectRepository.findAll();
    }




    // 프로젝트 상세 조회

    // 프로젝트 생성
    @Override
    public int createProject(ProjectDto dto) {

        int result = 0;

        Project projectEntity = dto.toEntity();

        String projectName = projectEntity.getName();

        if(validateDuplicateProject(projectName)){
            projectRepository.save(projectEntity);
            result = projectEntity.getProjectId();
        }

        return result;
    }

    // 프로젝트 수정

    // 프로젝트 삭제
    @Override
    public int updateStatus(int id) {

        int result = projectRepository.updateStatus(id);

        return 0;
    }

    // 중복 프로젝트 검증 - 프로젝트 생성 시
    private boolean validateDuplicateProject(String projectName){
        boolean answer = true;

        List project = projectRepository.findByName(projectName);


        if(!project.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("[프로젝트 생성 실패]");
        }

        return answer;
    }



}
