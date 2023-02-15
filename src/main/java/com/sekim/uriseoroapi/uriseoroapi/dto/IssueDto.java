package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Board;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import lombok.*;
import org.json.simple.JSONObject;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueDto {

     Map<String,Object> issue;

    /* Dto -> Entity */
     public Issue toEntity(){

           int projectId = 0;

           int trackerId = 0 ;

           int statusId = 0;

           int priorityId = 0;

           String subject = "";

           String description = "";

           String startDate = "";

           String dueDate = "";

           int assignedId = 0;

           int authorId = 0;

           int doneRatio = 0;


          projectId = (Integer) issue.get("project_id");
          trackerId  = (Integer) issue.get("tracker_id");
          statusId = (Integer) issue.get("status_id");
          priorityId= (Integer) issue.get("priority_id");
          subject = (String) issue.get("subject");
          description = (String) issue.get("description");
          startDate = (String) issue.get("start_date");
          dueDate = (String)issue.get("due_date");
          assignedId = (Integer) issue.get("assigned_to_id");
          authorId = (Integer)issue.get("author_id");
          doneRatio =(Integer) issue.get("done_ratio");


          Issue res = Issue.builder()
                  .project_id(projectId)
                  .tracker_id(trackerId)
                  .status_id(statusId)
                  .subject(subject)
                  .description(description)
                  .start_date(startDate)
                  .due_date(dueDate)
                  .author_id(authorId)
                  .done_ratio(doneRatio)
                  .priority_id(priorityId)
                  .assigned_to_id(assignedId)
                  .build();

          return res;
     }

}
