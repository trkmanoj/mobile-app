package com.mnj.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {
    private UUID id;
    private String fileName;
    private String filePath;
    private String mimeType;
    private Long fileSize;
}
