package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Project;
import lombok.*;

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
        String identifier = "";

        projectName = (String) project.get("name");
        description = (String) project.get("description");
        identifier = (String) project.get("identifier");

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
                .identifier(identifier)
                .build();

        return res;
    }


    @Getter
    public static class Response{

        private final int projectId;

        private final String name;

        private final String description;

        private final int isPublic;

        private final int status;

        private final String delYN;


        public Response(Project project){

            this.projectId = project.getProjectId();
            this.name = project.getName();
            this.description = project.getDescription();
            this.isPublic = project.getIsPublic();
            this.status = project.getStatus();
            this.delYN = project.getDelYN();

        }


    }



}
