package com.mnj.mobile.controller;

import com.mnj.mobile.dto.CommentDTO;
import com.mnj.mobile.dto.PathogensDTO;
import com.mnj.mobile.dto.ScmDTO;
import com.mnj.mobile.dto.SubCategoryDTO;
import com.mnj.mobile.service.MasterService;
import com.mnj.mobile.util.CommonConst;
import com.mnj.mobile.util.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/master")
@CrossOrigin("*")
public class MasterController {


    private MasterService masterService;

    @GetMapping("/pathogens/{type}")
    public ResponseEntity<CommonResponse> findPathogensByType(@PathVariable("type") String type){
        log.info("MasterController::findPathogensByType type {}", type);
        CommonResponse commonResponse = new CommonResponse();

        try{

            List<PathogensDTO> response = masterService.findPathogensByType(type);

            if (response.isEmpty()) {
                commonResponse.setErrorMessages(Collections.singletonList("Not found records."));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("MasterController::findPathogensByType response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);

        }catch (Exception ex){
            log.error("CommentController:findPathogensByType error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subCategory/{id}")
    public ResponseEntity<CommonResponse> findSubCategoryByPathogen(@PathVariable("id") Integer id){
        log.info("MasterController::findSubCategoryByPathogen id {}", id);
        CommonResponse commonResponse = new CommonResponse();

        try{

            List<SubCategoryDTO> response = masterService.findSubCategoryByPathogen(id);

            if (response.isEmpty()) {
                commonResponse.setErrorMessages(Collections.singletonList("Not found records."));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("MasterController::findSubCategoryByPathogen response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);

        }catch (Exception ex){
            log.error("MasterController:findSubCategoryByPathogen error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/scm/{id}")
    public ResponseEntity<CommonResponse> findScmToolsBySubCategory(@PathVariable("id") Integer id){
        log.info("MasterController::findScmToolsBySubCategory id {}", id);
        CommonResponse commonResponse = new CommonResponse();

        try{

            List<ScmDTO> response = masterService.findScmToolsBySubCategory(id);

            if (response.isEmpty()) {
                commonResponse.setErrorMessages(Collections.singletonList("Not found records."));
                commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            } else {
                commonResponse.setStatus(CommonConst.SUCCESS_CODE);
                commonResponse.setPayload(Collections.singletonList(response));
            }
            log.info("MasterController::findScmToolsBySubCategory response {}", HttpStatus.OK.value());
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);

        }catch (Exception ex){
            log.error("MasterController:findScmToolsBySubCategory error {}", ex.getMessage());
            return new ResponseEntity<>(new CommonResponse(CommonConst.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
