package com.aspire.thi.domain;


import java.util.Date;
import java.util.List;

/**
 * The Class ThiScore.
 */
public class ThiScore extends DatabaseObject {

	/** The project id. */
	private Integer projectId;

	/** The auditor id. */
	private Integer auditorId;
	/** The audit date. */
	private Date auditDate;

	/** The audit cycle date. */
	private Date auditCycleDate;

	/** The overall score. */
	private Double overallScore;

	/** The comments. */
	private String comments;

	/** The recommendations. */
	private String recommendations;

	/** The project owner id. */
	private String projectOwnerId;

	private Integer assesmentType;

	/** The auditor id. */
	private String auditorName;

	private String projectName;

	private Boolean auditComplete = Boolean.FALSE;

	private List<AssesmentGroupScore> assesmentGroupScores;
	
	private List<String> auditeeNames;


	/**
	 * Gets the audit date.
	 * 
	 * @return the audit date
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * Sets the audit date.
	 * 
	 * @param auditDate
	 *            the new audit date
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * Gets the audit cycle date.
	 * 
	 * @return the audit cycle date
	 */
	public Date getAuditCycleDate() {
		return auditCycleDate;
	}

	/**
	 * Sets the audit cycle date.
	 * 
	 * @param auditCycleDate
	 *            the new audit cycle date
	 */
	public void setAuditCycleDate(Date auditCycleDate) {
		this.auditCycleDate = auditCycleDate;
	}

	/**
	 * Gets the overall score.
	 * 
	 * @return the overall score
	 */
	public Double getOverallScore() {
		return overallScore;
	}

	/**
	 * Sets the overall score.
	 * 
	 * @param overallScore
	 *            the new overall score
	 */
	public void setOverallScore(Double overallScore) {
		this.overallScore = overallScore;
	}

	/**
	 * Gets the recommendations.
	 * 
	 * @return the recommendations
	 */
	public String getRecommendations() {
		return recommendations;
	}

	/**
	 * Sets the recommendations.
	 * 
	 * @param recommendations
	 *            the new recommendations
	 */
	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	/**
	 * Gets the project owner id.
	 * 
	 * @return the project owner id
	 */
	public String getProjectOwnerId() {
		return projectOwnerId;
	}

	/**
	 * Sets the project owner id.
	 * 
	 * @param projectOwnerId
	 *            the new project owner id
	 */
	public void setProjectOwnerId(String projectOwnerId) {
		this.projectOwnerId = projectOwnerId;
	}

	public void setAssesmentGroupScores(List<AssesmentGroupScore> assesmentGroupScores) {
		this.assesmentGroupScores = assesmentGroupScores;
	}

	public List<AssesmentGroupScore> getAssesmentGroupScores() {
		return assesmentGroupScores;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAssesmentType(Integer assesmentType) {
		this.assesmentType = assesmentType;
	}

	public Integer getAssesmentType() {
		return assesmentType;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setAuditComplete(Boolean auditComplete) {
		this.auditComplete = auditComplete;
	}

	public Boolean getAuditComplete() {
		return auditComplete;
	}

	/**
	 * @return the auditeeNames
	 */
	public List<String> getAuditeeNames() {
		return auditeeNames;
	}

	/**
	 * @param auditeeNames the auditeeNames to set
	 */
	public void setAuditeeNames(List<String> auditeeNames) {
		this.auditeeNames = auditeeNames;
	}
	
	

}
