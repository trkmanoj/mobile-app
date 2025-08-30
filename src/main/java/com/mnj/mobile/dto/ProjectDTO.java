package com.mnj.mobile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnj.mobile.enums.Status;
import com.mnj.mobile.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private UUID projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
//    private Team team;
    private Set<UUID> teamMembers;
    private List<CommonAttachmentDTO> attachments;
    private Status projectStatus;
    private boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
//    private Map<Status, Long> taskCount;
    private Map<Status, Long> taskCount;

    // add team and members to this constructor
    public ProjectDTO(UUID projectId, String name, LocalDate startDate, LocalDate endDate, List<CommonAttachmentDTO> attachments, Status projectStatus, boolean status, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.projectId = projectId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attachments = attachments;
        this.projectStatus = projectStatus;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public ProjectDTO(UUID projectId, String name, LocalDate startDate, LocalDate endDate, Status projectStatus, boolean status, LocalDateTime createdDate, LocalDateTime modifiedDate, Map<Status, Long> taskCount) {
         this.projectId=projectId;
         this.name=name;
         this.startDate=startDate;
         this.endDate=endDate;
         this.projectStatus=projectStatus;
         this.status=status;
         this.createdDate=createdDate;
         this.modifiedDate=modifiedDate;
         this.taskCount = taskCount;

    }
}
