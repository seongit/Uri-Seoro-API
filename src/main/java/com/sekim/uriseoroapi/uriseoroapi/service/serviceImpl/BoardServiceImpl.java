package com.sekim.uriseoroapi.uriseoroapi.service.serviceImpl;

import com.sekim.uriseoroapi.uriseoroapi.dto.BoardDto;
import com.sekim.uriseoroapi.uriseoroapi.model.Board;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import com.sekim.uriseoroapi.uriseoroapi.repository.BoardRepository;
import com.sekim.uriseoroapi.uriseoroapi.repository.UserRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 백업용
//    @Override
//    public Long save(BoardDto.Request dto) {
//        // User 정보를 가져와서 dto에 담아줌
//        //UserEntity user = userRepository.findByUserNo(userNo);
//        //dto.setUser(user);
//        Board board = dto.toEntity();
//        boardRepository.save(board);
//        return board.getId();
//    }

    @Override
    @Transactional
    public Long save(BoardDto.Request dto) {

        // User 정보를 가져와서 dto에 담아줌
        User user = userRepository.findByUsername(dto.getWriter());
        dto.setUser(user);
        Board board = dto.toEntity();

        boardRepository.save(board);
        return board.getId();
    }


//    @Override
//    @Transactional
//    public BoardDto.Response findById(Long id) {
//        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id:" + id));
//        return new BoardDto.Response(board);
//    }

    @Override
    @Transactional
    public BoardDto.Response findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id:" + id));

        // 게시글 조회수 업데이트
        boardRepository.updateView(id);

        return new BoardDto.Response(board);
    }

    /* UPDATE (dirty checking 영속성 컨텍스트) */
    @Override
    @Transactional
    public void update(Long id, BoardDto.Request dto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        board.update(dto.getTitle(), dto.getContent());
    }

    @Override
    @Transactional
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }

    @Override
    @Transactional
    public int updateStatus(Long id) {
        return boardRepository.updateStatus(id);
    }

    @Override
    @Transactional
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Board> boardList(){
//        return boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        return boardRepository.findAll();
    }

}
