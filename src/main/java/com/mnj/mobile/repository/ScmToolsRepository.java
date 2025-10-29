package com.mnj.mobile.repository;

import com.mnj.mobile.entity.ScmTools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScmToolsRepository extends JpaRepository<ScmTools, Integer> {
    @Query(value = "select * from scm_tool where sub_category_id = ?1",nativeQuery = true)
    List<ScmTools> findBySubCategory(Integer id);

    ScmTools findByName(String name);
}
