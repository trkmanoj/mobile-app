package com.mnj.mobile.service;

import com.mnj.mobile.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    String createMember(MemberDTO dto);

    List<MemberDTO> findAll();
}
