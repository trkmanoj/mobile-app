package com.mnj.mobile.dto;

import com.mnj.mobile.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstName;
    private String lastName;
    private String username;
    private boolean status;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
