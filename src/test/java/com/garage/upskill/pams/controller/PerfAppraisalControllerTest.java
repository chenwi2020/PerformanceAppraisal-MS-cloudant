package com.garage.upskill.pams.controller;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.garage.upskill.pams.domain.PerfAppraisalDoc;
import com.garage.upskill.pams.service.PerfAppraisalService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerfAppraisalControllerTest {
	@Mock
	PerfAppraisalService perfAppraisalService;
	
	PerfAppraisalController perfAppraisalController;
	
	PerfAppraisalDoc perfAppraisalDoc;
	Map<String, String> responseFailMsg;
	Map<String, String> responseSuccMsg;
	
	MockMvc mockMvc;
	
	//@SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);

        perfAppraisalController = new PerfAppraisalController(perfAppraisalService);
        mockMvc = MockMvcBuilders.standaloneSetup(perfAppraisalController).build();

        perfAppraisalDoc = new PerfAppraisalDoc("1", "Meets Expectation", "On time when the visitors arrive");
        
        responseFailMsg = new HashMap<String, String>();
        responseFailMsg.put("status", "FAILURE");
    	responseFailMsg.put("message", "No performance appraisal was found for employee id");
    	
    	responseSuccMsg = new HashMap<String, String>();
        responseSuccMsg.put("status", "SUCCESS");
        
    }
	
	@Test
    public void testGetPerfAppraisalByEmployeeId() throws Exception {
		when(perfAppraisalService.getPerfAppraisalByEmployeeId("1")).thenReturn(perfAppraisalDoc);
		mockMvc.perform(get("/getPerfAppraisalByEmployeeId/1"))
        .andExpect(status().isOk());
	}

	@Test
    public void testCreatePerfAppraisal() throws Exception {
		when(perfAppraisalService.createPerfAppraisal(perfAppraisalDoc)).thenReturn(responseSuccMsg);
		String jsonString = "{ \"empId\": \"1\", \"rating\": \"Meets Expectation\", \"feedback\": \"On time when the visitors arrive\" }";
		mockMvc.perform(post("/createPerfAppraisal").contentType(MediaType.APPLICATION_JSON).content(jsonString))
        .andExpect(status().isOk());
	}
	
	@Test
    public void testUpdatePerfAppraisal() throws Exception {
		mockMvc.perform(post("/updatePerfAppraisal"))
        .andExpect(status().isBadRequest());
	}
	
	@Test
    public void testdeletePerfAppraisalByEmployeeId() throws Exception {
		when(perfAppraisalService.deletePerfAppraisalByEmployeeId("123")).thenReturn(responseFailMsg);
		mockMvc.perform(delete("/deletePerfAppraisalByEmployeeId/123"))
        .andExpect(status().isBadRequest());
	}

}
