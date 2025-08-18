package com.mnj.mobile.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String team;
    private String teamMember;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId",referencedColumnName = "projectId")
    private List<Attachment> attachments;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
