package com.garage.upskill.pams.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.garage.upskill.pams.domain.PerfAppraisalDoc;
import com.garage.upskill.pams.service.PerfAppraisalService;

@RestController
public class PerfAppraisalController {

	private PerfAppraisalService perfAppraisalService;

    @Autowired
    public PerfAppraisalController (PerfAppraisalService perfAppraisalService) {
        this.perfAppraisalService = perfAppraisalService;
    }
    
    @GetMapping("/getPerfAppraisalByEmployeeId/{id}")
    public ResponseEntity<PerfAppraisalDoc> getPerfAppraisalByEmployeeId(@PathVariable String id) {
		PerfAppraisalDoc result = perfAppraisalService.getPerfAppraisalByEmployeeId(id);
    	if ("No performance appraisal was found for employee id".equals(result.getEmpId()))
    		return new ResponseEntity<PerfAppraisalDoc> (result, HttpStatus.BAD_REQUEST);
    	else
    		return new ResponseEntity<PerfAppraisalDoc> (result, HttpStatus.OK);
    }
    
    @PostMapping("/createPerfAppraisal")
    public ResponseEntity<Map<String, String>> createPerfAppraisal(@RequestBody PerfAppraisalDoc perfAppraisalDoc) {
    	Map<String, String> result = perfAppraisalService.createPerfAppraisal(perfAppraisalDoc);
    	if ("SUCCESS".equals(result.get("status")))
    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/updatePerfAppraisal")
    public ResponseEntity<Map<String, String>> updatePerfAppraisal(@RequestBody PerfAppraisalDoc perfAppraisalDoc) {
    	Map<String, String> result = perfAppraisalService.updatePerfAppraisal(perfAppraisalDoc);
    	if ("SUCCESS".equals(result.get("status")))
    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/deletePerfAppraisalByEmployeeId/{id}")
    public ResponseEntity<Map<String, String>> deletePerfAppraisalByEmployeeId(@PathVariable String id) {
    	Map<String, String> result = perfAppraisalService.deletePerfAppraisalByEmployeeId(id);
    	if ("SUCCESS".equals(result.get("status")))
    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.BAD_REQUEST);
    }
}
