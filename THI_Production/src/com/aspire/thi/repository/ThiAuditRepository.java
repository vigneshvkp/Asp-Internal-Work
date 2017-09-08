package com.aspire.thi.repository;

import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.AssesmentGroupScore;
import com.aspire.thi.domain.LineItemLog;
import com.aspire.thi.domain.ThiScore;

public interface ThiAuditRepository {

	public List<AssesmentGroupScore> getAssesmentGroupScores(Integer thiScoreId);

	public List<LineItemLog> getLineItemLogs(Integer assesmentGroupScoreId);

	public ThiScore getThiScore(Integer projectId, Date auditCycleDate);

}