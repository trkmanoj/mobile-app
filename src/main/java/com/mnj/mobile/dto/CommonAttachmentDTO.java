package com.mnj.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonAttachmentDTO {
    private String imageOriginalName;
    private String mimeType;
   private Long fileSize;
    private byte[] data;
    private String filePath;
}
