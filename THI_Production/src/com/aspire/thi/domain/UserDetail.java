package com.aspire.thi.domain;

import java.util.List;

public class UserDetail {

	private String aceNo;
	private String userStringId;
	private String userName; 
	private String email;
	private String level;
	private String deptId;
	private String duId;
	private boolean isAuditor;
	private int auditorId;
	private List<Integer> projectIds;
	private List<Integer> auditProjectIds;
	
	
	// to make sure this object gets created only from Login Controller
	public UserDetail(String userName){
		this.userName = userName;
	}
	
	/**
	 * @return the aceNo
	 */
	public String getAceNo() {
		return aceNo;
	}
	
	/**
	 * @param aceNo the aceNo to set
	 */
	public void setAceNo(String aceNo) {
		this.aceNo = aceNo;
	}
	
	/**
	 * @return the userStringId
	 */
	public String getUserStringId() {
		return userStringId;
	}
	
	/**
	 * @param userStringId the userStringId to set
	 */
	public void setUserStringId(String userStringId) {
		this.userStringId = userStringId;
	}
	
	/**
	 * @return the name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	
	/**
	 * @return the deptName
	 */
	public String getDeptId() {
		return deptId;
	}
	
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	/**
	 * @return the duId
	 */
	public String getDuId() {
		return duId;
	}
	
	/**
	 * @param duId the duId to set
	 */
	public void setDuId(String duId) {
		this.duId = duId;
	}
	
	/**
	 * @return the isAuditor
	 */
	public boolean isAuditor() {
		return isAuditor;
	}
	
	/**
	 * @param isAuditor the isAuditor to set
	 */
	public void setAuditor(boolean isAuditor) {
		this.isAuditor = isAuditor;
	}
	

	/**
	 * 
	 * @return the auditorId
	 */
	public int getAuditorId() {
		return auditorId;
	}
	
	/**
	 * 
	 * @param auditorId the auditorId to set
	 */
	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}

	/**
	 * @return the projectIds
	 */
	public List<Integer> getProjectIds() {
		return projectIds;
	}
	
	/**
	 * @param projects the projectsIds to set
	 */
	public void setProjectIds(List<Integer> projectIds) {
		this.projectIds = projectIds;
	}

	/**
	 * @return the prosProjectIds
	 */
	public List<Integer> getAuditProjectIds() {
		return auditProjectIds;
	}
	
	/**
	 * @param projects the prosProjectIds to set
	 */
	public void setAuditProjectIds(List<Integer> auditProjectIds) {
		this.auditProjectIds = auditProjectIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDetail [aceNo=").append(aceNo)
				.append(", userStringId=").append(userStringId)
				.append(", userName=").append(userName).append(", email=")
				.append(email).append(", level=").append(level)
				.append(", deptId=").append(deptId).append(", duId=")
				.append(duId).append(", isAuditor=").append(isAuditor)
				.append(", auditorId=").append(auditorId)
				.append(", projectIds=").append(projectIds)
				.append(", auditProjectIds=").append(auditProjectIds)
				.append("]");
		return builder.toString();
	}
	
	
	
}
