package com.mnj.mobile.service;

import com.mnj.mobile.dto.ProjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
    String createProject(MultipartFile[] files, ProjectDTO project) throws IOException;

    ProjectDTO findById(String projectId);

    List<ProjectDTO> findAll();

    List<ProjectDTO> findActiveAll(String status);
}
