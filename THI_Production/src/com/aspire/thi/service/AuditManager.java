package com.aspire.thi.service;

import java.io.Serializable;
import java.util.Date;

import com.aspire.thi.domain.ThiScore;

public interface AuditManager extends Serializable{

	public ThiScore getThiScore(Integer projectId, Date auditCycleDate);

}