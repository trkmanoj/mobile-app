package com.mnj.mobile.repository;

import com.mnj.mobile.entity.Pathogens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathogensRepository extends JpaRepository<Pathogens, Integer> {
    List<Pathogens> findByType(String type);
}
