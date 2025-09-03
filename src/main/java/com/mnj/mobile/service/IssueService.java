package com.mnj.mobile.service;

import com.mnj.mobile.dto.IssueDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IssueService {
    String createIssue(MultipartFile[] file, String issue) throws IOException;

    List<IssueDTO> findIssuesByProject(String projectId);
}
