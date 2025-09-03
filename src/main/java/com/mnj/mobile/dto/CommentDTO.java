package com.mnj.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private UUID id;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private boolean status;
    private String issue;

    public CommentDTO(UUID id, String description, LocalDateTime createdTime, LocalDateTime modifiedTime, boolean status) {
        this.id = id;
        this.description = description;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.status = status;
    }
}
