package com.sekim.uriseoroapi.uriseoroapi.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Table(name="tb_issue")
public class Issue {

    @Id @GeneratedValue
    @Column(name = "id")
    private long id;

//    @Column(columnDefinition = "int", nullable = false)
//    private int tracker_id;
//
//    @Column(columnDefinition = "int(11)", nullable = false)
//    private int project_id;
//
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String subject;
//
//    @Column(columnDefinition = "int(11)", nullable = false)
//    private int status_id;
//
//    @Column(columnDefinition = "int(11)", nullable = false)
//    private int author_id;
//
//    @Column(columnDefinition = "int(11)", nullable = false)
//    private int priority_id;
//
//    @Column(columnDefinition = "int(11)", nullable = false)
//    private int lock_version;
//
//    @Column(columnDefinition = "int(11)", nullable = false)
//    private int done_ratio;
//
//    @Column(columnDefinition = "tinyint(1)", nullable = false)
//    private int is_private;
//
    @Column(columnDefinition = "longtext")
    private String description;
//
//    @Column(columnDefinition = "date")
//    private String due_date;
//
//    @Column(columnDefinition = "date")
//    private String start_date;
//
//    @Column(columnDefinition = "date")
//    @CreatedDate
//    private String created_on;


}
