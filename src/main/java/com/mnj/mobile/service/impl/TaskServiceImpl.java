package com.mnj.mobile.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnj.mobile.dto.AttachmentDTO;
import com.mnj.mobile.dto.CommonAttachmentDTO;
import com.mnj.mobile.dto.TaskDTO;
import com.mnj.mobile.entity.Task;
import com.mnj.mobile.entity.TaskAttachment;
import com.mnj.mobile.repository.ProjectRepository;
import com.mnj.mobile.repository.TaskRepository;
import com.mnj.mobile.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private ProjectRepository projectRepository;

    private final ObjectMapper objectMapper;

    @Value("${application.attachment}")
    private String filePath;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String createTask(MultipartFile[] files, String taskStr) throws IOException {
        log.info("TaskServiceImpl:createTask execution started.");

        TaskDTO taskDTO = objectMapper.readValue(taskStr, TaskDTO.class);

        List<TaskAttachment> list = new ArrayList<>();
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

            TaskAttachment attachment = new TaskAttachment(
                    null,
                    file.getOriginalFilename(),
                    targetPath.toString(),
                    file.getContentType(),
                    file.getSize()
            );


            list.add(attachment);
        }

        Task task = new Task(
                taskDTO.getTaskId(),
                taskDTO.getName(),
                taskDTO.getStartDate(),
                taskDTO.getEndDate(),
                taskDTO.getTeam(),
                projectRepository.findById(UUID.fromString(taskDTO.getProject())).get(),
                list,
                taskDTO.getTaskStatus(),
                taskDTO.isStatus(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        taskRepository.save(task);
        log.info("TaskServiceImpl:createTask execution ended.");
        return "success.";
    }

    @Override
    public List<TaskDTO> findByProject(String projectId) {
        log.info("TaskServiceImpl:findByProject execution started.");

        List<Task> tasks = taskRepository.findByProjectProjectId(UUID.fromString(projectId));

        List<TaskDTO> taskDTOS = tasks.stream().map(task -> new TaskDTO(
                task.getTaskId(),
                task.getName(),
                task.getStartDate(),
                task.getEndDate(),
                task.getTeam(),
                task.getProject().getProjectId().toString(),
                task.getAttachments().stream()
                        .map(attachment ->
                                new CommonAttachmentDTO(
                                        attachment.getFileName(),
                                        attachment.getMimeType(),
                                        attachment.getFileSize(),
                                        safeGetImagePathBytes(attachment.getFilePath())
                                )).collect(Collectors.toList()),
                task.getTaskStatus(),
                task.isStatus(),
                task.getCreatedDate(),
                task.getModifiedDate()
        )).collect(Collectors.toList());

        log.info("TaskServiceImpl:findByProject execution started.");
        return taskDTOS;
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
