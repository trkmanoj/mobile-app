package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.PathogensDTO;
import com.mnj.mobile.dto.ScmDTO;
import com.mnj.mobile.dto.SubCategoryDTO;
import com.mnj.mobile.entity.Pathogens;
import com.mnj.mobile.entity.ScmTools;
import com.mnj.mobile.entity.SubCategory;
import com.mnj.mobile.repository.PathogensRepository;
import com.mnj.mobile.repository.ScmToolsRepository;
import com.mnj.mobile.repository.SubCategoryRepository;
import com.mnj.mobile.service.MasterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MasterServiceImpl implements MasterService {

    private PathogensRepository pathogensRepository;

    private SubCategoryRepository subCategoryRepository;

    private ScmToolsRepository scmToolsRepository;


    @Override
    public List<PathogensDTO> findPathogensByType(String type) {
        log.info("MasterServiceImpl:findPathogensByType execution started.");
        List<Pathogens> pathogens = pathogensRepository.findByType(type);

        List<PathogensDTO> pathogensDTOS = pathogens.stream().map(pathogen -> new PathogensDTO(
                pathogen.getId(),
                pathogen.getName(),
                pathogen.getType(),
                pathogen.isStatus()
        )).collect(Collectors.toList());

        log.info("MasterServiceImpl:findPathogensByType execution ended.");
        return pathogensDTOS;
    }

    @Override
    public List<SubCategoryDTO> findSubCategoryByPathogen(Integer id) {
        log.info("MasterServiceImpl:findSubCategoryByPathogen execution started.");
        List<SubCategory> subCategories = subCategoryRepository.findByPathogens(id);

        List<SubCategoryDTO> list = subCategories.stream().map(subCategory -> new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getName(),
                subCategory.isStatus()
        )).collect(Collectors.toList());

        log.info("MasterServiceImpl:findSubCategoryByPathogen execution ended.");
        return list;
    }

    @Override
    public List<ScmDTO> findScmToolsBySubCategory(Integer id) {
        log.info("MasterServiceImpl:findScmToolsBySubCategory execution started.");
        List<ScmTools> scmTools = scmToolsRepository.findBySubCategory(id);

        List<ScmDTO> list = scmTools.stream().map(scm -> new ScmDTO(
                scm.getId(),
                scm.getName(),
                scm.isStatus()
        )).collect(Collectors.toList());

        log.info("MasterServiceImpl:findScmToolsBySubCategory execution started.");
        return list;
    }
}
