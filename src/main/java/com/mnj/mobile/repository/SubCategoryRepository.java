package com.mnj.mobile.repository;

import com.mnj.mobile.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    @Query(value = "select * from sub_category where pathogens_id = ?1", nativeQuery = true)
    List<SubCategory> findByPathogens(Integer id);
}
