package com.mnj.mobile.repository;

import com.mnj.mobile.entity.Task;
import com.mnj.mobile.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByProjectProjectId(UUID projectId);
    List<Task>findByProjectProjectIdAndStatusTrue(UUID projectId);
    List<Task> findByTaskStatusAndStatus(Status taskStatus, boolean status);

    List<Task> findByStatusTrue();
}
