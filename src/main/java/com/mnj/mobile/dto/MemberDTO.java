package com.mnj.mobile.dto;

import com.mnj.mobile.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private UUID id;
    private String name;
    private String email;
    private String mobile;
    private Team team;
    private boolean status;
}
