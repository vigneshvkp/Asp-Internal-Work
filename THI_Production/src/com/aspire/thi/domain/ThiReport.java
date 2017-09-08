package com.aspire.thi.domain;

import java.util.Map;
import java.util.Date;

public class ThiReport {

	private Integer thiScoreId;

	private String projectName;

	private Double overallScore;

	private String assesmentType;
	
	private Date auditDate;

	private Map<String, Double> assesmentGroupScore;

	public void setThiScoreId(Integer thiScoreId) {
		this.thiScoreId = thiScoreId;
	}

	public Integer getThiScoreId() {
		return thiScoreId;
	}

	public void setOverallScore(Double overallScore) {
		this.overallScore = overallScore;
	}

	public Double getOverallScore() {
		return overallScore;
	}

	public void setAssesmentType(String assesmentType) {
		this.assesmentType = assesmentType;
	}

	public String getAssesmentType() {
		return assesmentType;
	}

	public void setAssesmentGroupScore(Map<String, Double> assesmentGroupScore) {
		this.assesmentGroupScore = assesmentGroupScore;
	}

	public Map<String, Double> getAssesmentGroupScore() {
		return assesmentGroupScore;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * @param auditDate the auditDate to set
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	

}
