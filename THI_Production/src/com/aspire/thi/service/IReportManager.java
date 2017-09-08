package com.aspire.thi.service;

import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.ThiReport;

public interface IReportManager {

	public List<ThiReport> getDUThiReport(Integer duId, Date cycleDate);
	
	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate, Date toDate);
	
	public List<ThiReport> getOrgThiReport(Integer duId, Date cycleDate);

}
