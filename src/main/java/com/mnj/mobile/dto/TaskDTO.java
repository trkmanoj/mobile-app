package com.mnj.mobile.dto;

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
public class TaskDTO {
    private UUID taskId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String team;
    private String project;
    private List<AttachmentDTO> attachments;
    private Status taskStatus;
    private boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
