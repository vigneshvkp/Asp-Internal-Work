package com.aspire.thi.domain;

import java.util.Date;

/**
 * @author vishnu.nehru
 *
 */
public class Project extends DatabaseObject {

	private int prosProjectId;
	private String projectName;
	private boolean active;
	private Date startDate;
	private Date endDate;
	private String name;
	private int auditFrequency;
	private int customerId;
	private int deptId;
	private String ownerUserId;
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getProsProjectId() {
		return prosProjectId;
	}
	public void setProsProjectId(int prosProjectId) {
		this.prosProjectId = prosProjectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		StringBuilder project = new StringBuilder(super.toString());
		project.append("Project [prosProjectId=").append(prosProjectId)
			.append(", projectName=").append(projectName)
			.append(", customerId=").append(customerId)
			.append(", deptId=").append(deptId)
			.append(", ownerUserId=").append(ownerUserId)
			.append(", active=").append(active)
			.append(", startDate=").append(startDate)
			.append(", endDate=").append(endDate)
			.append("]");
		return project.toString();
	}

	public int getAuditFrequency() {
		return auditFrequency;
	}

	public void setAuditFrequency(int auditFrequency) {
		this.auditFrequency = auditFrequency;
	}
}
