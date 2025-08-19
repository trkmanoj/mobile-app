package com.mnj.mobile.service;

import com.mnj.mobile.dto.TaskDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    String createTask(MultipartFile[] files, String task) throws IOException;

    List<TaskDTO> findByProject(String projectId);
}
