package com.mnj.mobile.entity;

import com.mnj.mobile.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;
    private String name;
    private String email;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private Team team;
    private String designation;
    private boolean status = true;
    @ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", referencedColumnName = "imageId")
    private MemberImage image;

    public Member(UUID id, String name, String email, String mobile, Team team, String designation, boolean status, MemberImage image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.team = team;
        this.designation = designation;
        this.status = status;
        this.image = image;
    }
}
