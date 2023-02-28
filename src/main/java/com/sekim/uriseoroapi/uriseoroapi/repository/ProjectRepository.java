package com.sekim.uriseoroapi.uriseoroapi.repository;

import com.sekim.uriseoroapi.uriseoroapi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository  extends JpaRepository <Project, Integer> {

    @Modifying
    @Query("update Project p set p.delYN = 'Y' where p.projectId = :id and p.delYN = 'N'")
    int updateStatus(int id);


    @Modifying
    @Query("select p from Project p where p.name = :projectName and p.delYN = 'N'")
    List findByName(String projectName);
}
