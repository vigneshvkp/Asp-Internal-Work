package com.aspire.thi.service;

import java.util.List;

import com.aspire.thi.domain.Auditor;

public class AuditorHelper {
	 List<Auditor> auditors;
	 List<Auditor> emps;
	 String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Auditor> getEmps() {
		return emps;
	}
	public void setEmps(List<Auditor> emps) {
		this.emps = emps;
	}
	public List<Auditor> getAuditors() {
		return auditors;
	}
	public void setAuditors(List<Auditor> auditors) {
		this.auditors = auditors;
	}
}