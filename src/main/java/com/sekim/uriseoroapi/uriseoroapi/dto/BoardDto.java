package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Board;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import lombok.*;

public class BoardDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private Long id;
        private String title;
        private String writer;
        private String content;
        private String createdDate, modifiedDate;
        private int view;
        private User user;
        private String delYN;

        /* Dto -> Entity */

        public Board toEntity() {
            Board board = Board.builder()
                    .id(id)
                    .title(title)
                    .writer(writer)
                    .content(content)
                    .view(0)
                    .user(user)
                    .delYN(delYN)
                    .build();

            return board;
        }
    }

    /**
     * 게시글 정보를 리턴하는 응답 클래스
     * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
     */

    @Getter
    public static class Response {
        private final Long id;
        private final String title;
        private final String writer;
        private final String content;
        private final String createdDate, modifiedDate;

        private final int view;

        private final Long userNo;

        private final String delYN;

        /* Entity -> Dto */
        public Response(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.content = board.getContent();
            this.createdDate = board.getCreatedDate();
            this.modifiedDate = board.getModifiedDate();
            this.view = board.getView();
            this.userNo = board.getUser().getUserNo();
            this.delYN = board.getDelYN();
        }

    }

}
