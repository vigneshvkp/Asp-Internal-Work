package com.aspire.thi.domain;

public class Department extends DatabaseObject {

	private String prosDeptId;
	private String name;
	private String deptHeadAceNo;
	/**
	 * @return the prosDeptId
	 */
	public String getProsDeptId() {
		return prosDeptId;
	}
	/**
	 * @param prosDeptId the prosDeptId to set
	 */
	public void setProsDeptId(String prosDeptId) {
		this.prosDeptId = prosDeptId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the deptHeadAceNo
	 */
	public String getDeptHeadAceNo() {
		return deptHeadAceNo;
	}
	/**
	 * @param deptHeadAceNo the deptHeadAceNo to set
	 */
	public void setDeptHeadAceNo(String deptHeadAceNo) {
		this.deptHeadAceNo = deptHeadAceNo;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("Department [prosDeptId=").append(prosDeptId)
				.append(", name=").append(name).append(", deptHeadAceNo=")
				.append(deptHeadAceNo).append("]");
		return builder.toString();
	}
	
	
	
}
