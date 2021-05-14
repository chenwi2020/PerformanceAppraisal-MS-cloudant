package com.garage.upskill.pams.controller;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.garage.upskill.pams.domain.PerfAppraisalDoc;
import com.garage.upskill.pams.service.PerfAppraisalService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerfAppraisalControllerTest {
	@Mock
	PerfAppraisalService perfAppraisalService;
	
	PerfAppraisalController perfAppraisalController;
	
	PerfAppraisalDoc perfAppraisalDoc;
	
	MockMvc mockMvc;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        perfAppraisalController = new PerfAppraisalController(perfAppraisalService);
        mockMvc = MockMvcBuilders.standaloneSetup(perfAppraisalController).build();

        perfAppraisalDoc = new PerfAppraisalDoc("123", "Meets Expectation", "developer");
        
    }
	
	@Test
    public void testGetPerfAppraisalByEmployeeId() throws Exception {
		mockMvc.perform(get("/getPerfAppraisalByEmployeeId/1"))
        .andExpect(status().isNotFound());
	}

	@Test
    public void testSavePerfAppraisal() throws Exception {
		mockMvc.perform(post("/savePerfAppraisal"))
        .andExpect(status().isBadRequest());
	}
	
	@Test
    public void testUpdatePerfAppraisal() throws Exception {
		mockMvc.perform(post("/updatePerfAppraisal"))
        .andExpect(status().isBadRequest());
	}
	
	@Test
    public void testdeletePerfAppraisalByEmployeeId() throws Exception {
		mockMvc.perform(delete("/deletePerfAppraisalByEmployeeId/1"))
        .andExpect(status().isNotFound())
		.andExpect(content().string("FAILURE|No performance appraisal was found for employee id"));
	}

}
