package com.mnj.mobile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID issueId;
    private String description;
    private String projectId;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "issueId",referencedColumnName = "issueId")
    private List<IssueAttachment> attachments;
    private boolean status;
}
