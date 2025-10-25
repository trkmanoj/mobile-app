package com.mnj.mobile.service;

import com.mnj.mobile.dto.PathogensDTO;
import com.mnj.mobile.dto.ScmDTO;
import com.mnj.mobile.dto.SubCategoryDTO;

import java.util.List;

public interface MasterService {
    List<PathogensDTO> findPathogensByType(String type);

    List<SubCategoryDTO> findSubCategoryByPathogen(Integer id);

    List<ScmDTO> findScmToolsBySubCategory(Integer id);
}
