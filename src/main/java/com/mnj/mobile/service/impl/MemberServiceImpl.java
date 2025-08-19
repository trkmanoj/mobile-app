package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.MemberDTO;
import com.mnj.mobile.entity.Member;
import com.mnj.mobile.repository.MemberRepository;
import com.mnj.mobile.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Override
    public String createMember(MemberDTO dto) {
        log.info("MemberServiceImpl:createMember execution started.");

        Member member = new Member(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getMobile(),
                dto.getTeam(),
                dto.isStatus()
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
                                member.isStatus()
                        )).collect(Collectors.toList());

        log.info("MemberServiceImpl:findAll execution ended.");
        return memberDTOS;
    }
}
