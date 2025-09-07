package com.mnj.mobile.service;

import com.mnj.mobile.dto.TaskDTO;
import com.mnj.mobile.dto.TaskResponseDTO;
import com.mnj.mobile.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    String createTask(MultipartFile[] files, TaskDTO task) throws IOException;

    List<TaskDTO> findByProject(String projectId);

    TaskResponseDTO findById(String taskId);

    List<TaskDTO> findByStatus(Status status);
}
