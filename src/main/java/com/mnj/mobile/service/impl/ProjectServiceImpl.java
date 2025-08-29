package com.mnj.mobile.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnj.mobile.dto.CommonAttachmentDTO;
import com.mnj.mobile.dto.MemberDTO;
import com.mnj.mobile.dto.ProjectDTO;
import com.mnj.mobile.entity.Attachment;
import com.mnj.mobile.entity.Project;
import com.mnj.mobile.entity.Task;
import com.mnj.mobile.enums.Status;
import com.mnj.mobile.repository.MemberRepository;
import com.mnj.mobile.repository.ProjectRepository;
import com.mnj.mobile.repository.TaskRepository;
import com.mnj.mobile.service.ProjectService;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private MemberRepository memberRepository;

    private TaskRepository taskRepository;

    private final ObjectMapper objectMapper;

    @Value("${application.attachment}")
    private String filePath;

    public ProjectServiceImpl(ProjectRepository projectRepository, MemberRepository memberRepository, TaskRepository taskRepository, ObjectMapper objectMapper) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String createProject(MultipartFile[] files, ProjectDTO projectDTO) throws IOException {
        log.info("ProjectServiceImpl:createProject execution started.");

//        ProjectDTO projectDTO = objectMapper.readValue(projectStr, ProjectDTO.class);

        List<Attachment> list = new ArrayList<>();
        if (files != null && files.length > 0) {

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

            Attachment attachment = new Attachment(
                    null,
                    file.getOriginalFilename(),
                    targetPath.toString(),
                    file.getContentType(),
                    file.getSize()
            );


            list.add(attachment);
        }
        }
        Set<UUID> memberIds = new HashSet<>();
        memberIds=projectDTO.getTeamMembers();
//        if (projectDTO.getTeamMembers() != null){
//            memberIds = projectDTO.getTeamMembers().stream().collect(Collectors.toSet());
//        }
   Status status=projectDTO.getProjectStatus();
        Project project = new Project(
                projectDTO.getProjectId(),
                projectDTO.getName(),
                projectDTO.getStartDate(),
                projectDTO.getEndDate(),
//                projectDTO.getTeam(),
                list,
                status,
                projectDTO.isStatus(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                !memberIds.isEmpty() ? new HashSet<>(memberRepository.findAllById(memberIds)) : null
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
//                project.getTeam(),
//                project.getMembers().stream().map(member ->
//                        new MemberDTO(
//                                member.getId(),
//                                member.getName(),
//                                member.getEmail(),
//                                member.getMobile(),
//                                member.getTeam(),
//                                member.getDesignation(),
//                                member.isStatus()
//                        )).collect(Collectors.toSet()),
                project.getAttachments().stream().map(attachment ->
                        new CommonAttachmentDTO(
                                attachment.getFileName(),
                                attachment.getMimeType(),
                                attachment.getFileSize(),
                                safeGetImagePathBytes(attachment.getFilePath()),
                                attachment.getFilePath()
                        )).collect(Collectors.toList()),
                Status.PENDING,
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
//                project.getTeam(),
//                project.getMembers().stream().map(member ->
//                        new MemberDTO(
//                                member.getId(),
//                                member.getName(),
//                                member.getEmail(),
//                                member.getMobile(),
//                                member.getTeam(),
//                                member.getDesignation(),
//                                member.isStatus()
//                        )).collect(Collectors.toSet()),
                project.getAttachments().stream().map(
                        attachment -> new CommonAttachmentDTO(
                                attachment.getFileName(),
                                attachment.getMimeType(),
                                attachment.getFileSize(),
                                safeGetImagePathBytes(attachment.getFilePath()),
                                attachment.getFilePath()
                        )).collect(Collectors.toList()),
                project.getProjectStatus(),
                project.isStatus(),
                project.getCreatedDate(),
                project.getModifiedDate()
        )).collect(Collectors.toList());

        log.info("ProjectServiceImpl:findAll execution ended.");
        return projectDTOS;
    }

    @Override
    public List<ProjectDTO> findActiveAll(String status) {
        log.info("ProjectServiceImpl:findAll execution started.");
        Status projectStatus = Status.valueOf(status.toUpperCase());
        List<Project> projects = projectRepository.findByStatusTrueAndProjectStatus(projectStatus);
        List<ProjectDTO> projectDTOS = projects.stream()
                .sorted(Comparator.comparing(Project::getModifiedDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .map(project ->
                {
                    List<Task> tasks = taskRepository.findByProjectProjectId(project.getProjectId());

                    return new ProjectDTO(
                            project.getProjectId(),
                            project.getName(),
                            project.getStartDate(),
                            project.getEndDate(),
                            project.getProjectStatus(),
                            project.isStatus(),
                            project.getCreatedDate(),
                            project.getModifiedDate(),
                            tasks.stream()
                                    .collect(Collectors.groupingBy(Task::getTaskStatus, Collectors.counting()))
                    );
                })
                .collect(Collectors.toList());

        log.info("ProjectServiceImpl:findAll execution ended.");
        return projectDTOS;
    }

    @Override
    public boolean updateProjectStatus( String projectId, String status) {
        try {
            Status status1=Status.valueOf(status.toUpperCase());
            int updatedRows = projectRepository.updateProjectStatusById(UUID.fromString(projectId), status1);

            log.info("Number of rows updated: {}", updatedRows);
            return updatedRows > 0;
        } catch (Exception ex) {
            log.error("Exception while updating project status", ex);
            return false;
        }
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
