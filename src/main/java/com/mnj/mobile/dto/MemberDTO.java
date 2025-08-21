package com.mnj.mobile.dto;

import com.mnj.mobile.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private String designation;
    private boolean status;
    private AttachmentDTO attachment;

    public MemberDTO(UUID id, String name, String email, String mobile, Team team, String designation, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.team = team;
        this.designation = designation;
        this.status = status;
    }
}
