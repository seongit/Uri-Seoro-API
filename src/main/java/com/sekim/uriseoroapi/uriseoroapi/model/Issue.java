package com.sekim.uriseoroapi.uriseoroapi.model;

import com.sekim.uriseoroapi.uriseoroapi.dto.IssueDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Table(name="tb_issue")
public class Issue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(columnDefinition = "int(11)", nullable = false)
    private int project_id;

    @Column(columnDefinition = "int", nullable = false)
    private int tracker_id;

    @Column(columnDefinition = "int(11)", nullable = false)
    private int status_id;

    @Column(columnDefinition = "int(11)", nullable = false)
    private int priority_id;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String subject;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column(columnDefinition = "date")
    private String due_date;

    @Column
     //private int assigned_to_id;
    private String assigned_to_id;

    @Column(columnDefinition = "int(11)", nullable = false)
    private int author_id;

    @Column(columnDefinition = "int(11) default 0", nullable = false)
    private int done_ratio;

    @Column(columnDefinition = "tinyint(1) default 0", nullable = false)
    private int is_private;

    @Column(columnDefinition = "date")
    private String start_date;

    @Column(columnDefinition = "date")
    @CreatedDate
    private String created_on; // 이슈 생성 일자

    @Column(columnDefinition = "date")
    @CreatedDate
    private String updated_on; // 이슈 변경 일자

    @Column(columnDefinition = "date")
    @CreatedDate
    private String closed_on; // 이슈 삭제 일자

    @ColumnDefault("'N'")
    private String delYN;


    /* 해당 엔티티를 저장하기 이전에 실행 */
    @PrePersist
    public void onPrePersist(){
        this.created_on = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.updated_on = this.created_on;
    }

    /* 해당 엔티티를 업데이트 하기 이전에 실행*/
    @PreUpdate
    public void onPreUpdate(){
        this.updated_on = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    // 일감 수정
    public int update(IssueDto issueDto){

        if(issueDto.getIssue().get("project_id") != null){
            this.project_id = (Integer) issueDto.getIssue().get("project_id");
        }

        if(issueDto.getIssue().get("tracker_id") != null){
            this.tracker_id = (Integer) issueDto.getIssue().get("tracker_id");
        }

        if(issueDto.getIssue().get("status_id") != null){
            this.status_id = (Integer) issueDto.getIssue().get("status_id");
        }

        if(issueDto.getIssue().get("priority_id")!= null){
            this.priority_id = (Integer)issueDto.getIssue().get("priority_id");
        }


        if(issueDto.getIssue().get("subject")!= null){
            this.subject = (String) issueDto.getIssue().get("subject");
        }

        if(issueDto.getIssue().get("description") != null){
            this.description = (String) issueDto.getIssue().get("description");
        }

        if( issueDto.getIssue().get("due_date") != null){
            this.due_date = (String) issueDto.getIssue().get("due_date");
        }


        if(issueDto.getIssue().get("assigned_to_id")!= null){

            System.out.println("ssueDto.getIssue().get(\"assigned_to_id\")====>"+ issueDto.getIssue().get("assigned_to_id"));
            this.assigned_to_id =  issueDto.getIssue().get("assigned_to_id").toString();
        }

        if(issueDto.getIssue().get("author_id")!= null){
            this.author_id = (Integer)issueDto.getIssue().get("author_id");
        }

        if(issueDto.getIssue().get("done_ratio")!= null){
            this.done_ratio = (Integer)issueDto.getIssue().get("done_ratio");
        }

//        this.is_private = (Integer) issueDto.getIssue().get("is_private");

        if(issueDto.getIssue().get("start_date") != null){
            this.start_date = (String) issueDto.getIssue().get("start_date");
        }

        if( issueDto.getIssue().get("updated_on") != null ){
            this.updated_on = (String) issueDto.getIssue().get("updated_on");
        }


        return this.id;
    }




}
