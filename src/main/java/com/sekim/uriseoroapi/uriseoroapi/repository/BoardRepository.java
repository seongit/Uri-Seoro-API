package com.sekim.uriseoroapi.uriseoroapi.repository;

import com.sekim.uriseoroapi.uriseoroapi.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    @Modifying
    @Query("update Board b set b.view = b.view +1 where b.id = :id")
    int updateView(Long id);


    @Modifying
    @Query("update Board b set b.delYN = 'Y' where b.id = :id and b.delYN = 'N'")
    int updateStatus(Long id);

    //Page <Board> findAll(Pageable pageable);

    // 게시판 전체 목록 조회
    @Query("select b from Board b where b.delYN = 'N' order by b.id desc ")
    List<Board> findAll();


}
