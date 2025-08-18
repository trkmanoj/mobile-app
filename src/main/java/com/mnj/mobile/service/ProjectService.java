package com.mnj.mobile.service;

import com.mnj.mobile.dto.ProjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProjectService {
    String createProject(MultipartFile[] files, String project) throws IOException;

    ProjectDTO findById(String projectId);
}
