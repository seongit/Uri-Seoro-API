package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

    Map<String,Object> project;

    /* Dto -> Entity */
    public Project toEntity() {

        String projectName = "";
        String description = "";
        int isPublic = 1;
        int status = 1;
        String delYN = "N";

        projectName = (String) project.get("name");
        description = (String) project.get("description");

        if((boolean) project.get("is_public") == false ){
            isPublic = 0;
        }
//        status = (int) project.get("status");


        Project res = Project.builder()
                .name(projectName)
                .description(description)
                .isPublic(isPublic)
                .status(status)
                .delYN(delYN)
                .build();

        return res;
    }

}
