package com.sekim.uriseoroapi.uriseoroapi.model;

import lombok.*;
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

    @Id @GeneratedValue
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
    private int assigned_to_id;

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


}
