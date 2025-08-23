package com.mnj.mobile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnj.mobile.enums.Status;
import com.mnj.mobile.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private UUID projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Team team;
    private Set<MemberDTO> teamMembers;
    private List<CommonAttachmentDTO> attachments;
    private Status projectStatus;
    private boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
