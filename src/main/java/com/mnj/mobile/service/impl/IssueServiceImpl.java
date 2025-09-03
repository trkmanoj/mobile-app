package com.mnj.mobile.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnj.mobile.dto.CommonAttachmentDTO;
import com.mnj.mobile.dto.IssueDTO;
import com.mnj.mobile.entity.*;
import com.mnj.mobile.repository.IssueRepository;
import com.mnj.mobile.service.IssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IssueServiceImpl implements IssueService {

    private IssueRepository issueRepository;

    private final ObjectMapper objectMapper;

    @Value("${application.attachment}")
    private String filePath;

    public IssueServiceImpl(IssueRepository issueRepository, ObjectMapper objectMapper) {
        this.issueRepository = issueRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String createIssue(MultipartFile[] files, String issueStr) throws IOException {
        log.info("IssueServiceImpl:createIssue execution started.");

        IssueDTO dto = objectMapper.readValue(issueStr, IssueDTO.class);

        List<IssueAttachment> list = new ArrayList<>();
        if (files != null) {

            for (MultipartFile file : files) {
                Path uploadPath = Paths.get(filePath);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path targetPath = uploadPath.resolve(filename);

                // Best practice: use try-with-resources (auto-close stream)
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }

                IssueAttachment attachment = new IssueAttachment(
                        null,
                        file.getOriginalFilename(),
                        targetPath.toString(),
                        file.getContentType(),
                        file.getSize()
                );


                list.add(attachment);
            }
        }

        Issue issue = new Issue(
                dto.getIssueId(),
                dto.getDescription(),
                dto.getProjectId(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                list,
                dto.isStatus(),
                dto.getIssueStatus()
        );


        issueRepository.save(issue);
        log.info("IssueServiceImpl:createIssue execution ended.");
        return "success.";
    }

    @Override
    public List<IssueDTO> findIssuesByProject(String projectId) {
        log.info("IssueServiceImpl:findIssuesByProject execution started.");

        List<Issue> issues = issueRepository.findByProjectId(projectId);

        List<IssueDTO> issueDTOS = issues.stream().map(issue ->
                new IssueDTO(
                        issue.getIssueId(),
                        issue.getDescription(),
                        issue.getProjectId(),
                        issue.getCreatedTime(),
                        issue.getModifiedTime(),
                        issue.getAttachments().stream().map(attachment -> new CommonAttachmentDTO(
                                attachment.getFileName(),
                                attachment.getMimeType(),
                                attachment.getFileSize(),
                                safeGetImagePathBytes(attachment.getFilePath()),
                                attachment.getFilePath()
                                )).collect(Collectors.toList()),
                        issue.isStatus(),
                        issue.getIssueStatus()
                )).collect(Collectors.toList());

        log.info("IssueServiceImpl:findIssuesByProject execution ended.");
        return issueDTOS;
    }

    @Override
    public IssueDTO findIssueById(String issueId) {
        log.info("IssueServiceImpl:findIssueById execution started.");

        if (!issueRepository.existsById(UUID.fromString(issueId))) {
            return null;
        }

        Issue issue = issueRepository.findById(UUID.fromString(issueId)).get();

        IssueDTO issueDTO = new IssueDTO(
                issue.getIssueId(),
                issue.getDescription(),
                issue.getProjectId(),
                issue.getCreatedTime(),
                issue.getModifiedTime(),
                issue.getAttachments().stream().map(attachment -> new CommonAttachmentDTO(
                        attachment.getFileName(),
                        attachment.getMimeType(),
                        attachment.getFileSize(),
                        safeGetImagePathBytes(attachment.getFilePath()),
                        attachment.getFilePath()
                )).collect(Collectors.toList()),
                issue.isStatus()
        );

        log.info("IssueServiceImpl:findIssueById execution ended.");
        return issueDTO;
    }

    private byte[] getImagePathBytes(String imgUrl) throws IOException {
        Path targetLocation = Paths.get(imgUrl);
        return Files.readAllBytes(targetLocation);
    }

    private byte[] safeGetImagePathBytes(String imgUrl) {
        try {
            return getImagePathBytes(imgUrl);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
