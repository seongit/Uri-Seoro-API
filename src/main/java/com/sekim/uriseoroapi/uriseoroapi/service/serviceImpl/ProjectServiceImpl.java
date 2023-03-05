package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.ProjectDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Project;
import com.sekim.uriseoroapi.uriseoroapi.repository.ProjectRepository;
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
    @Override
    public ProjectDto.Response getProjectDetail(int projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException());

        return new ProjectDto.Response(project);
    }

    // 프로젝트 생성
    @Override
    public int createProject(ProjectDto dto) {

        int result = 0;

        Project projectEntity = dto.toEntity();

        String projectName = projectEntity.getName();
        String identifier = projectEntity.getIdentifier();

        if(validateDuplicateProject(projectName,identifier)){
            projectRepository.save(projectEntity);
            result = projectEntity.getProjectId();
        }

        return result;
    }


    // 프로젝트 수정
    @Override
    public int update(int id, ProjectDto dto) {


        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        // 변경하고자하는 프로젝트 이름
        String toChangeName = (String) dto.getProject().get("name");

        String originName = project.getName();

        // 프로젝트 ID
        int updatedProjectId = 0;

        if(!toChangeName.equals(originName)){
            if(validateDuplicateProjectName(toChangeName)){
                updatedProjectId = project.update(dto);
            }
        }else{
            updatedProjectId = project.update(dto);
        }

        return updatedProjectId;
    }


    // 프로젝트 삭제
    @Override
    public int updateStatus(int id) {

        int projectId = projectRepository.updateStatus(id);

        return projectId;
    }

    // 중복 프로젝트 검증 - 프로젝트 생성 시
    private boolean validateDuplicateProject(String projectName, String identifier){
        boolean answer = true;

        List project = projectRepository.findByNameAndIdentifier(projectName,identifier);


        if(!project.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("[프로젝트 생성 실패] 프로젝트명과 식별자를 확인해주세요");
        }

        return answer;
    }


    // 중복 프로젝트명 검증 - 프로젝트 수정 시
    private boolean validateDuplicateProjectName(String projectName){
        boolean answer = true;

        List project = projectRepository.findByName(projectName);


        if(!project.isEmpty()){
            answer = false;
        }

        // EXCEPTION 발생 시키기
        if(answer == false){
            throw  new IllegalArgumentException("[프로젝트 생성 실패] 프로젝트명과 식별자를 확인해주세요");
        }

        return answer;
    }




}
