package com.aspire.thi.web;

import java.util.Date;

public class ThiForm {

	private Integer projectId;

	private Integer assesmentTypeId;

	private Date auditCycle;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getAssesmentTypeId() {
		return assesmentTypeId;
	}

	public void setAssesmentTypeId(Integer assesmentTypeId) {
		this.assesmentTypeId = assesmentTypeId;
	}

	public Date getAuditCycle() {
		return auditCycle;
	}

	public void setAuditCycle(Date auditCycle) {
		this.auditCycle = auditCycle;
	}

}
