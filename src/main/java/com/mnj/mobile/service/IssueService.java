package com.mnj.mobile.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IssueService {
    String createIssue(MultipartFile[] file, String issue) throws IOException;
}
