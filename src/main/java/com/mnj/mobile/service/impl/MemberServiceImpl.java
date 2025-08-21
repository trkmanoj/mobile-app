package com.mnj.mobile.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnj.mobile.dto.AttachmentDTO;
import com.mnj.mobile.dto.CommonAttachmentDTO;
import com.mnj.mobile.dto.MemberDTO;
import com.mnj.mobile.entity.Attachment;
import com.mnj.mobile.entity.Member;
import com.mnj.mobile.entity.MemberImage;
import com.mnj.mobile.repository.MemberRepository;
import com.mnj.mobile.service.MemberService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j

public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    private final ObjectMapper objectMapper;

    @Value("${application.attachment}")
    private String filePath;

    public MemberServiceImpl(MemberRepository memberRepository, ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String createMember(MultipartFile file, String memberStr) throws IOException {
        log.info("MemberServiceImpl:createMember execution started.");

        MemberDTO dto = objectMapper.readValue(memberStr, MemberDTO.class);

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

        MemberImage attachment = new MemberImage(
                null,
                file.getOriginalFilename(),
                targetPath.toString(),
                file.getContentType(),
                file.getSize()
        );

        Member member = new Member(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getMobile(),
                dto.getTeam(),
                dto.getDesignation(),
                dto.isStatus(),
                attachment
        );


        memberRepository.save(member);
        log.info("MemberServiceImpl:createMember execution ended.");
        return "success.";
    }

    @Override
    public List<MemberDTO> findAll() {
        log.info("MemberServiceImpl:findAll execution started.");

        List<MemberDTO> memberDTOS = memberRepository.findAll().stream()
                .map(member -> new MemberDTO(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getMobile(),
                        member.getTeam(),
                        member.getDesignation(),
                        member.isStatus(),
                        new CommonAttachmentDTO(
                                member.getImage().getFileName(),
                                member.getImage().getMimeType(),
                                member.getImage().getFileSize(),
                                safeGetImagePathBytes(member.getImage().getFilePath()))
                )).collect(Collectors.toList());

        log.info("MemberServiceImpl:findAll execution ended.");
        return memberDTOS;
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
