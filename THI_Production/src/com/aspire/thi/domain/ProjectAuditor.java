package com.aspire.thi.domain;

import java.util.Date;

public class ProjectAuditor extends DatabaseObject {

	private Boolean auditComplete;
	private Integer auditorId;
	private Integer projectId;
	private Date auditDate;

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

}

