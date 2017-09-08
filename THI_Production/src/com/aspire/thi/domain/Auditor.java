package com.aspire.thi.domain;

public class Auditor extends DatabaseObject{
	
	private String aceNo; 
	private String name;
	private boolean active;
	private String deptID;
	private String deptName;
	private String email;
	
	public String getAceNo() {
		return aceNo;
	}
	public void setAceNo(String aceNo) {
		this.aceNo = aceNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDeptID() {
		return deptID;
	}
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (aceNo.equals(((Auditor)obj).getAceNo()))
			return true;
		return false;
	}
}
