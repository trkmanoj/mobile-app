package com.mnj.mobile.repository;

import com.mnj.mobile.entity.Project;
import com.mnj.mobile.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByStatusTrue();

    List<Project> findByStatusTrueAndProjectStatus(Status status);
    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.projectStatus = :status WHERE p.id = :projectId")
    int updateProjectStatusById(UUID projectId, Status status);
}
