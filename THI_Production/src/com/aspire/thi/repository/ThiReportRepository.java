package com.aspire.thi.repository;

import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.ThiReport;

public interface ThiReportRepository {

	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate);
	
	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate, Date toDate);
	
	public List<ThiReport> getOrgThiReport(Integer duId, Date auditCycleDate);
}
