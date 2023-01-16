package com.sekim.uriseoroapi.uriseoroapi.controller;

import com.sekim.uriseoroapi.uriseoroapi.dto.BoardDto;
import com.sekim.uriseoroapi.uriseoroapi.repository.UserRepository;
import com.sekim.uriseoroapi.uriseoroapi.service.BoardService;
import com.sekim.uriseoroapi.uriseoroapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final UserService userService;

    /* 게시글 작성 */
    @PostMapping("/write")
    public ResponseEntity save(@RequestBody BoardDto.Request dto){
        return ResponseEntity.ok(boardService.save(dto));
    }

    /* 게시글 전체 목록 조회 */
//    @GetMapping("/read")
//    public ResponseEntity read(){
//
//        System.out.println("api server read() 호출됨");
//
//        return ResponseEntity.ok(boardService.boardList());
//    }

    /* 테스트 */
    @GetMapping("/read")
    public ResponseEntity read(){
        return ResponseEntity.ok(boardService.boardList());
    }


    /* 게시글 상세 조회 */
    @GetMapping("/read/{id}")
    public ResponseEntity read(@PathVariable Long id){
        return ResponseEntity.ok(boardService.findById(id));
    }

    /* 게시글 수정 */
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody BoardDto.Request dto){
        boardService.update(id,dto);
        return ResponseEntity.ok(id);
    }

    /* 게시글 삭제 delYn의 값을 변경한다. (delYn default value N) */
    @PatchMapping("/delete/{id}")
    public ResponseEntity update(@PathVariable Long id) {

        System.out.println("api server "+id);


        return ResponseEntity.ok(boardService.updateStatus(id));
    }


}
