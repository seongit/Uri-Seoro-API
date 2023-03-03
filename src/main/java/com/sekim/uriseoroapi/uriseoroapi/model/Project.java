package com.sekim.uriseoroapi.uriseoroapi.model;

import com.sekim.uriseoroapi.uriseoroapi.dto.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Builder
@Getter
@Entity
@Table(name="tb_project")
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="projectId")
    private int projectId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "int(11)", nullable = false)
    @ColumnDefault("1")
    private int isPublic;

    @Column(columnDefinition = "int(11)", nullable = false)
    @ColumnDefault("1")
    private int status;

    @ColumnDefault("'N'")
    private String delYN;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String createdDate;

    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String modifiedDate;

    @Column
    private String identifier;



    /* 해당 엔티티를 저장하기 이전에 실행 */
    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createdDate;
    }


    /* 해당 엔티티를 업데이트 하기 이전에 실행*/
    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }


    public int update(ProjectDto projectDto){

        if(projectDto.getProject().get("id") != null){
            this.projectId = (Integer)projectDto.getProject().get("id");
        }

        if(projectDto.getProject().get("name") != null){
            this.name = (String) projectDto.getProject().get("name");
        }

        if(projectDto.getProject().get("description") != null){
            this.description = (String) projectDto.getProject().get("description");
        }

        if(projectDto.getProject().get("is_public") != null){
            if((boolean) projectDto.getProject().get("is_public") == true){
                this.isPublic = 1;
            }else {
                this.isPublic = 0;
            }
        }

        return this.projectId;
    }



}
