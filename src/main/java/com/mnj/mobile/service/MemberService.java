package com.mnj.mobile.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mnj.mobile.dto.MemberDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    String createMember(MultipartFile files, String member) throws IOException;

    List<MemberDTO> findAll();

    List<MemberDTO> findMemberByStatus(boolean status);
}
