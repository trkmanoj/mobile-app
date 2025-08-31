package com.mnj.mobile.entity;

import com.mnj.mobile.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID taskId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
//    private String team;
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId",referencedColumnName = "taskId")
    private List<TaskAttachment> attachments;
    @Enumerated(EnumType.STRING)
    private Status taskStatus = Status.PENDING;
    private boolean status = true;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @ManyToMany
    @JoinTable(
            name = "task_members",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();
}
