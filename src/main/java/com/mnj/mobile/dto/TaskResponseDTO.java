package com.mnj.mobile.dto;

import com.mnj.mobile.entity.Project;
import com.mnj.mobile.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {

    private UUID taskId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
//    private Team team;
    private List<MemberDTO> teamMembers;
    private List<CommonAttachmentDTO> attachments;
    private Status projectStatus;
    private boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Project project;
    private String description;



}
