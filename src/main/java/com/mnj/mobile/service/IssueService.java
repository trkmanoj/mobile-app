package com.mnj.mobile.service;

import com.mnj.mobile.dto.IssueDTO;
import com.mnj.mobile.dto.IssueResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IssueService {
    String createIssue(MultipartFile[] file, String issue) throws IOException;

    List<IssueResponse> findIssuesByProject(String projectId);

    IssueResponse findIssueById(String issueId);

    String updateIssue(MultipartFile[] files, String issueId, String desc) throws IOException;
}
