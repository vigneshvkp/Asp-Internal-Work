package com.aspire.thi.service;

import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.ThiReport;
import com.aspire.thi.repository.ThiReportRepository;

public class ThiReportManager implements IReportManager {

	private ThiReportRepository thiReportDao;
	@Override
	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate) {
		return thiReportDao.getDUThiReport(duId, auditCycleDate);
	}
	@Override
	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate, Date toDate){
		return thiReportDao.getDUThiReport(duId, auditCycleDate, toDate);
	}
	
	@Override
	public List<ThiReport> getOrgThiReport(Integer duId, Date auditCycleDate) {
		return thiReportDao.getOrgThiReport(duId, auditCycleDate);
	}

	public void setThiReportDao(ThiReportRepository thiReportDao) {
		this.thiReportDao = thiReportDao;
	}

	public ThiReportRepository getThiReportDao() {
		return thiReportDao;
	}

}
