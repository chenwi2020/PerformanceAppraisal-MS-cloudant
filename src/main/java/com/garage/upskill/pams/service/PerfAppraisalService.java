package com.garage.upskill.pams.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.Expression;
import com.garage.upskill.pams.domain.PerfAppraisalDoc;

@Service
public class PerfAppraisalService {
	@Autowired
	private CloudantClient client;
	private Database pa_db;
	
	@Value( "${pa_dbname}" )
	private String pa_dbname;
	
	public PerfAppraisalDoc getPerfAppraisalByEmployeeId(String empid) {
		System.out.println("getPerfAppraisalByEmployeeId");				
		pa_db = client.database(pa_dbname, false);
		Expression exp = Expression.eq("empId", empid);
		//String qb = new QueryBuilder(exp).build();
		//System.out.println("qb: "+qb);
		QueryResult<PerfAppraisalDoc> results = pa_db.query(new QueryBuilder(exp).build(), PerfAppraisalDoc.class);
		List<PerfAppraisalDoc> perfAppraisalDocList = results.getDocs();
		if (perfAppraisalDocList == null) {
			System.out.println("No PA documents found");
			return new PerfAppraisalDoc("No performance appraisal was found for employee id","N/A","N/A");
		}
		else if (perfAppraisalDocList.size() == 1) {
			System.out.println("PA document returned: "+perfAppraisalDocList.get(0));
			return perfAppraisalDocList.get(0);
		}
		else {
			System.out.println("ERROR: " +  perfAppraisalDocList.size() + " documents were returned");
			return new PerfAppraisalDoc("No performance appraisal was found for employee id","N/A","N/A");
		}
	}
	
	// create performance appraisal for employee id
    public Map<String, String> createPerfAppraisal(PerfAppraisalDoc perfAppraisalDoc) {
    	System.out.println("createPerfAppraisal");
    	System.out.println("Received perfAppraisalDoc: "+perfAppraisalDoc.toString());
    	Map<String, String> responseMsg = new HashMap<String, String>();
    	pa_db = client.database(pa_dbname, false);	
    	if ((perfAppraisalDoc.getEmpId() == null) || ("".equals(perfAppraisalDoc.getEmpId()))) {
    		System.out.println("Employee id was not provided in the request");
    		responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "Employee id was not provided in the request");
        	return responseMsg;
    	}
    	// Check if performance appraisal already exists for employee id
    	Expression exp = Expression.eq("empId", perfAppraisalDoc.getEmpId());
		//String qb = new QueryBuilder(exp).build();
		//System.out.println("qb: "+qb);
		QueryResult<PerfAppraisalDoc> results = null;
		try {
			results = pa_db.query(new QueryBuilder(exp).build(), PerfAppraisalDoc.class);
		}
		catch (NullPointerException e) {
			System.out.println("NullPointerException caught - no performance appraisal exists for employee id");
		}
		if (results.getDocs().size() == 0) {
			try {
				pa_db = client.database(pa_dbname, false);
				Response response = pa_db.save(perfAppraisalDoc);
				System.out.println("response PA _id: "+ response.getId());
				System.out.println("response _rev: "+ response.getRev());
	    		responseMsg.put("status", "SUCCESS");
			}
			catch (Exception e) {
	    		responseMsg.put("status", "FAILURE");
	        	responseMsg.put("message", e.getMessage());
			}
		}
		else {
    		responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "Performance appraisal already exists for employee id");
		}
		return responseMsg;
    }
    
    // update performance appraisal for employee id
    public Map<String, String> updatePerfAppraisal(PerfAppraisalDoc perfAppraisalDoc) {
    	System.out.println("updatePerfAppraisal");
    	System.out.println("Received perfAppraisalDoc: "+perfAppraisalDoc.toString());
    	Map<String, String> responseMsg = new HashMap<String, String>();
    	pa_db = client.database(pa_dbname, false);
    	if ((perfAppraisalDoc.getEmpId() == null) || ("".equals(perfAppraisalDoc.getEmpId()))) {
    		System.out.println("Employee id was not provided in the request");
    		responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "Employee id was not provided in the request");
        	return responseMsg;
    		//return "FAILURE|Employee id was not provided in the request";
    	}
    	// Query to get _id and _rev value
    	Expression exp = Expression.eq("empId", perfAppraisalDoc.getEmpId());
		//String qb = new QueryBuilder(exp).build();
		//System.out.println("qb: "+qb);
		QueryResult<PerfAppraisalDoc> results = pa_db.query(new QueryBuilder(exp).build(), PerfAppraisalDoc.class);
		List<PerfAppraisalDoc> perfAppraisalDocList = results.getDocs();
		if (perfAppraisalDocList == null) {
			System.out.println("No PA documents found");
    		responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "No performance appraisal was found for employee id");
        	//return responseMsg;
			//responseMsg = "FAILURE|No performance appraisal was found for employee id";
		}
		else if (perfAppraisalDocList.size() == 0) {
			System.out.println("ERROR: 0 documents were returned");
    		responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "No performance appraisal was found for employee id");
			//responseMsg = "FAILURE|No performance appraisal was found for employee id";
		}
		else if (perfAppraisalDocList.size() != 1) {
			System.out.println("ERROR: " +  perfAppraisalDocList.size() + " documents were returned");
    		responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "Multiple performance appraisals were found for employee id");
		}
		else { //if (perfAppraisalDocList.size() == 1)
			System.out.println("PA document returned: "+perfAppraisalDocList.get(0));
			String id = perfAppraisalDocList.get(0).getId();
			String rev = perfAppraisalDocList.get(0).getRevision();
			perfAppraisalDoc.setId(id);
			perfAppraisalDoc.setRevision(rev);
			Response response = pa_db.update(perfAppraisalDoc);
			System.out.println("response PA _id: "+ response.getId());
			System.out.println("response _rev: "+ response.getRev());
			responseMsg.put("status", "SUCCESS");
			//responseMsg = "SUCCESS";
    	}
		return responseMsg;
    }
    
    // delete performance appraisal for employee id
    public Map<String, String> deletePerfAppraisalByEmployeeId(String empid) {
    	System.out.println("deletePerfAppraisalByEmployeeId");
    	Map<String, String> responseMsg = new HashMap<String, String>();
    	pa_db = client.database(pa_dbname, false);
    	// Query to get _id and _rev value
    	Expression exp = Expression.eq("empId", empid);
		//String qb = new QueryBuilder(exp).build();
		//System.out.println("qb: "+qb);
		QueryResult<PerfAppraisalDoc> results = pa_db.query(new QueryBuilder(exp).build(), PerfAppraisalDoc.class);
		List<PerfAppraisalDoc> perfAppraisalDocList = results.getDocs();
		if (perfAppraisalDocList == null) {
			System.out.println("No PA documents found for employee id");
			responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "No performance appraisal was found for employee id");
		}
		else if (perfAppraisalDocList.size() == 0) {
			System.out.println("ERROR: 0 documents were returned");
			responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "No performance appraisal was found for employee id");
		}
		else if (perfAppraisalDocList.size() != 1) {
			System.out.println("ERROR: " +  perfAppraisalDocList.size() + " documents were returned");
			responseMsg.put("status", "FAILURE");
        	responseMsg.put("message", "Multiple performance appraisals were found for employee id");
		}
		else { 
			System.out.println("PA document returned: "+perfAppraisalDocList.get(0));
			String id = perfAppraisalDocList.get(0).getId();
			String rev = perfAppraisalDocList.get(0).getRevision();
			PerfAppraisalDoc perfAppraisalDoc = new PerfAppraisalDoc();
			perfAppraisalDoc.setId(id);
			perfAppraisalDoc.setRevision(rev);			
			Response response = pa_db.remove(perfAppraisalDoc);
			System.out.println("response PA _id: "+ response.getId());
			System.out.println("response _rev: "+ response.getRev());
			responseMsg.put("status", "SUCCESS");
    	}
		return responseMsg;
    }
    
}
