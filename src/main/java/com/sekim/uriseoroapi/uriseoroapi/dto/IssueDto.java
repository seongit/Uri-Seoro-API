package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Board;
import com.sekim.uriseoroapi.uriseoroapi.model.Issue;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

public class IssueDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class Request {

        private Long id;

        private String subject;

        private String description;



        /* Dto -> Entity */

        public Issue toEntity() {
            Issue issue = Issue.builder()
                    .id(id)
                    .subject(subject)
                    .description(description)
                    .build();
            return issue;
        }


    }

}
