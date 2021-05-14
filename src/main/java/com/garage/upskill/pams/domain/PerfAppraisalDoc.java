package com.garage.upskill.pams.domain;

import com.cloudant.client.api.model.Document;

public class PerfAppraisalDoc extends Document{
    private String empId;
    private String rating;
    private String feedback;
    
    
	public PerfAppraisalDoc() {
		super();
	}

	public PerfAppraisalDoc(String empId, String rating, String feedback) {
		super();
		this.empId = empId;
		this.rating = rating;
		this.feedback = feedback;
	}
	
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "PerfAppraisalDoc [id=" + getId() + ", rev=" + getRevision() + ", empId=" + empId + ", rating=" + rating + ", feedback=" + feedback
				+ "]";
	}
}
