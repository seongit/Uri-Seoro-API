package com.sekim.uriseoroapi.uriseoroapi.service;
import com.sekim.uriseoroapi.uriseoroapi.dto.BoardDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    // Long save(BoardDto.Request dto, long userNo);

    Long save(BoardDto.Request dto);

    BoardDto.Response findById(Long id);

    void update(Long id, BoardDto.Request dto);

    int updateView(Long id);

   int updateStatus(Long id);

    Page<Board> boardList(Pageable pageable);

    // 전체 목록 조회
    List <Board> boardList();

}
