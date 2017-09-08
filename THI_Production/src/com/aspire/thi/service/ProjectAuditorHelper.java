package com.aspire.thi.service;

import java.util.Date;

import com.aspire.thi.domain.ProjectAuditor;

public class ProjectAuditorHelper {

	private int id;
	private Boolean auditComplete;
	private Integer auditorId;
	private Integer projectId;
	private Date auditDate;
	private String projectName;
	private String auditorName;
	
	public Boolean getAuditComplete() {
		return auditComplete;
	}
	public void setAuditComplete(Boolean auditComplete) {
		this.auditComplete = auditComplete;
	}
	public Integer getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public static ProjectAuditorHelper convertProjectAuditor(ProjectAuditor projectAuditor) {
		ProjectAuditorHelper projAuditor = new ProjectAuditorHelper();
		
		projAuditor.setId(projectAuditor.getId());
		projAuditor.setProjectId(projectAuditor.getProjectId());
		projAuditor.setAuditorId(projectAuditor.getAuditorId());
		projAuditor.setAuditComplete(projectAuditor.getAuditComplete());
		projAuditor.setAuditDate(projectAuditor.getAuditDate());
		
		return projAuditor;
	}
}
