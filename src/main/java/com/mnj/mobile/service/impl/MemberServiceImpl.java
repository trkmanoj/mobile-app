package com.mnj.mobile.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnj.mobile.dto.AttachmentDTO;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File directory = new File(filePath);
        if (!directory.exists()) directory.mkdirs();

        Path filePath = Paths.get(directory.getAbsolutePath(), fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Member member = getMember(file, filePath, dto);

        memberRepository.save(member);
        log.info("MemberServiceImpl:createMember execution ended.");
        return "success.";
    }

    private static Member getMember(MultipartFile file, Path filePath, MemberDTO dto) {
        MemberImage attachment = new MemberImage(
                null,
                file.getOriginalFilename(),
                filePath.toString(),
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
        return member;
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
                        new AttachmentDTO(
                                member.getImage().getImageId(),
                                member.getImage().getFileName(),
                                member.getImage().getFilePath(),
                                member.getImage().getMimeType(),
                                member.getImage().getFileSize())
                )).collect(Collectors.toList());

        log.info("MemberServiceImpl:findAll execution ended.");
        return memberDTOS;
    }
}
