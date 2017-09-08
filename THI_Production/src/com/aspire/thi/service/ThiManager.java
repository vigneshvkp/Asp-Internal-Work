package com.aspire.thi.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.AssesmentType;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.ThiScore;

public interface ThiManager extends Serializable{

	public ThiScore getThiScore(Integer projectId, Date auditCycleDate);

	public ProjectAuditor getProjectAuditor(Integer projectId, Date auditCycleDate);

	public ThiScore createNewAudit(Integer assignmentType);

	public void updateAudit(ThiScore score);

	public void saveAudit(ThiScore score);

	public List<AssesmentType> getAssesmentTypes();

	public List<String> getAssesmentCriteria(Integer assesmentGroupId);

	public void setAuditComplete(Boolean auditComplete, Integer projectId, Integer auditorId, Date auditCycleDate);
	
	public String getAssessmentGroupName(Integer assessmentGroupId);
	
	public void insertAuditee(int projectId,String[] auditee,String userAceNo);
	
	public List<String> getProjectAuditee(int projectId);
}