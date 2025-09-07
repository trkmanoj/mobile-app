package com.mnj.mobile.service;

import com.mnj.mobile.dto.ProjectDTO;
import com.mnj.mobile.dto.ProjectResponseDTO;
import com.mnj.mobile.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProjectService {
    String createProject(MultipartFile[] files, ProjectDTO project) throws IOException;

    ProjectResponseDTO findById(String projectId);

    List<ProjectDTO> findAll();

    List<ProjectDTO> findActiveAll(String status);

    boolean updateProjectStatus(String id, String status);


    Map<Status, Long> findActiveProjectCount();
}
