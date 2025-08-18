package com.mnj.mobile.dto;

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
public class ProjectDTO {

    private UUID projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String team;
    private String teamMember;
    private List<AttachmentDTO> attachments;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
