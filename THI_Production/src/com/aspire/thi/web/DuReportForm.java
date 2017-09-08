package com.aspire.thi.web;

import java.util.Date;

public class DuReportForm {

	private Integer departmentId;
	
	private Integer projectId;

	private Date auditCycle;
	
	private Date toDate;

	private Boolean exportAsPdf;

	public Date getAuditCycle() {
		return auditCycle;
	}

	public void setAuditCycle(Date auditCycle) {
		this.auditCycle = auditCycle;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setExportAsPdf(Boolean exportAsPdf) {
		this.exportAsPdf = exportAsPdf;
	}

	public Boolean getExportAsPdf() {
		return exportAsPdf;
	}

	/**
	 * @return the projectId
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	

}
