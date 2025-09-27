package com.mnj.mobile.repository;

import com.mnj.mobile.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByIssueIssueId(UUID issueId);

    @Modifying
    @Query(value = "update comment set description = ?2, modifiedTime = ?3 where id = ?1", nativeQuery = true)
    void updateComment(String commentId, String desc, LocalDateTime date);
}
