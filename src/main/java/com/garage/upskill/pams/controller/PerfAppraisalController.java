package com.garage.upskill.pams.controller;

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
    	if ("Employee Id Not Available".equals(result.getEmpId()))
    		return new ResponseEntity<PerfAppraisalDoc> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<PerfAppraisalDoc> (result, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/savePerfAppraisal")
    public ResponseEntity<String> savePerfAppraisal(@RequestBody PerfAppraisalDoc perfAppraisalDoc) {
		String result = perfAppraisalService.savePerfAppraisal(perfAppraisalDoc);
    	if ("SUCCESS".equals(result))
    		return new ResponseEntity<String> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<String> (result, HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/updatePerfAppraisal")
    public ResponseEntity<String> updatePerfAppraisal(@RequestBody PerfAppraisalDoc perfAppraisalDoc) {
    	String result = perfAppraisalService.updatePerfAppraisal(perfAppraisalDoc);
    	if ("SUCCESS".equals(result))
    		return new ResponseEntity<String> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<String> (result, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/deletePerfAppraisalByEmployeeId/{id}")
    public ResponseEntity<String> deletePerfAppraisalByEmployeeId(@PathVariable String id) {
    	String result = perfAppraisalService.deletePerfAppraisalByEmployeeId(id);
    	if ("SUCCESS".equals(result))
    		return new ResponseEntity<String> (result, HttpStatus.OK);
    	else
    		return new ResponseEntity<String> (result, HttpStatus.NOT_FOUND);
    }
}
