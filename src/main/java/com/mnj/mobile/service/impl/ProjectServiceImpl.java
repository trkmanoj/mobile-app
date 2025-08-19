package com.mnj.mobile.service.impl;

import com.google.gson.Gson;
import com.mnj.mobile.dto.AttachmentDTO;
import com.mnj.mobile.dto.MemberDTO;
import com.mnj.mobile.dto.ProjectDTO;
import com.mnj.mobile.entity.Attachment;
import com.mnj.mobile.entity.Member;
import com.mnj.mobile.entity.Project;
import com.mnj.mobile.repository.MemberRepository;
import com.mnj.mobile.repository.ProjectRepository;
import com.mnj.mobile.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private MemberRepository memberRepository;

    @Value("${application.attachment}")
    private String filePath;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public String createProject(MultipartFile[] files, ProjectDTO projectDTO) throws IOException {
        log.info("ProjectServiceImpl:createProject execution started.");

        List<Attachment> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File directory = new File(filePath);
            if (!directory.exists()) directory.mkdirs();

            Path filePath = Paths.get(directory.getAbsolutePath(), fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Attachment attachment = new Attachment(
                    null,
                    file.getOriginalFilename(),
                    filePath.toString(),
                    file.getContentType(),
                    file.getSize()
            );


            list.add(attachment);
        }

        Set<UUID> memberIds = projectDTO.getTeamMembers().stream().map(MemberDTO::getId).collect(Collectors.toSet());

        Project project = new Project(
                projectDTO.getProjectId(),
                projectDTO.getName(),
                projectDTO.getStartDate(),
                projectDTO.getEndDate(),
                projectDTO.getTeam(),
                list,
                projectDTO.getProjectStatus(),
                projectDTO.isStatus(),
                projectDTO.getCreatedDate(),
                projectDTO.getModifiedDate(),
                new HashSet<>(memberRepository.findAllById(memberIds))
        );

        projectRepository.save(project);
        log.info("ProjectServiceImpl:createProject execution ended.");
        return "success.";
    }

    @Override
    public ProjectDTO findById(String projectId) {
        log.info("ProjectServiceImpl:findById execution started.");

        Project project = projectRepository.findById(UUID.fromString(projectId)).get();

        ProjectDTO projectDTO = new ProjectDTO(
                project.getProjectId(),
                project.getName(),
                project.getStartDate(),
                project.getEndDate(),
                project.getTeam(),
                project.getMembers().stream().map(member ->
                        new MemberDTO(
                                member.getId(),
                                member.getName(),
                                member.getEmail(),
                                member.getMobile(),
                                member.getTeam(),
                                member.isStatus()
                        )).collect(Collectors.toSet()),
                project.getAttachments().stream().map(attachment ->
                        new AttachmentDTO(
                                attachment.getId(),
                                attachment.getFileName(),
                                attachment.getFilePath(),
                                attachment.getMimeType(),
                                attachment.getFileSize()
                        )).collect(Collectors.toList()),
                project.getProjectStatus(),
                project.isStatus(),
                project.getCreatedDate(),
                project.getModifiedDate()
        );

        log.info("ProjectServiceImpl:findById execution ended.");
        return projectDTO;
    }

    @Override
    public List<ProjectDTO> findAll() {
        log.info("ProjectServiceImpl:findAll execution started.");
        List<Project> projects = projectRepository.findAll();

        List<ProjectDTO> projectDTOS = projects.stream().map(project -> new ProjectDTO(
                project.getProjectId(),
                project.getName(),
                project.getStartDate(),
                project.getEndDate(),
                project.getTeam(),
                project.getMembers().stream().map(member ->
                        new MemberDTO(
                                member.getId(),
                                member.getName(),
                                member.getEmail(),
                                member.getMobile(),
                                member.getTeam(),
                                member.isStatus()
                        )).collect(Collectors.toSet()),
                project.getAttachments().stream().map(
                        attachment -> new AttachmentDTO(
                                attachment.getId(),
                                attachment.getFileName(),
                                attachment.getFilePath(),
                                attachment.getMimeType(),
                                attachment.getFileSize()
                        )).collect(Collectors.toList()),
                project.getProjectStatus(),
                project.isStatus(),
                project.getCreatedDate(),
                project.getModifiedDate()
        )).collect(Collectors.toList());

        log.info("ProjectServiceImpl:findAll execution ended.");
        return projectDTOS;
    }
}
