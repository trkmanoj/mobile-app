package com.mnj.mobile.repository;

import com.mnj.mobile.entity.Project;
import com.mnj.mobile.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByStatusTrue();

    List<Project> findByStatusTrueAndProjectStatus(Status status);
}
