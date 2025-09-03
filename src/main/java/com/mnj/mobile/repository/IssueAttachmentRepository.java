package com.mnj.mobile.repository;

import com.mnj.mobile.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IssueAttachmentRepository extends JpaRepository<Issue, UUID> {
    @Modifying
    @Query(value = "delete from issue where issueId = ?1", nativeQuery = true)
    void deleteByIssueId(String issueId);
}
