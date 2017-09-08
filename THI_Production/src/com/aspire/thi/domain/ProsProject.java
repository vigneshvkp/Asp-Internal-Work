package com.aspire.thi.domain;

import java.util.Date;

public class ProsProject extends DatabaseObject{
	
	private int prosProjectId;
	private String projectName;
	private int customerId;
	private int deptId;
	private boolean active;
	private String ownerUserId;
	private Date startDate;
	private Date endDate;

	/**
	 * @return the prosProjectId
	 */
	public int getProsProjectId() {
		return prosProjectId;
	}
	/**
	 * @param prosProjectId the prosProjectId to set
	 */
	public void setProsProjectId(int prosProjectId) {
		this.prosProjectId = prosProjectId;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the deptId
	 */
	public int getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the ownerUserId
	 */
	public String getOwnerUserId() {
		return ownerUserId;
	}
	/**
	 * @param ownerUserId the ownerUserId to set
	 */
	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("ProsProject [prosProjectId=").append(prosProjectId)
				.append(", projectName=").append(projectName)
				.append(", customerId=").append(customerId).append(", deptId=")
				.append(deptId).append(", active=").append(active)
				.append(", ownerUserId=").append(ownerUserId)
				.append(", startDate=").append(startDate).append(", endDate=")
				.append(endDate).append("]");
		return builder.toString();
	}
	
	
	
	
}
