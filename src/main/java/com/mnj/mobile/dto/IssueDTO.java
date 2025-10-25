package com.mnj.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    private UUID issueId;
    private String description;
    private String projectId;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private List<CommonAttachmentDTO> attachments;
    private boolean status;
    private String issueStatus;
    private String issueCategory;
    private String dispute;
    private String pathogen;
    private String subCategory;
    private String scm;

}
