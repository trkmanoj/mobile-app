package com.mnj.mobile.service;

import com.mnj.mobile.dto.TaskDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TaskService {
    String createTask(MultipartFile[] files, TaskDTO taskDTO) throws IOException;
}
