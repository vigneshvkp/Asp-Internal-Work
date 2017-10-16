package com.aspire.thi.repository;

import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.AssesmentGroupScore;
import com.aspire.thi.domain.AssesmentType;
import com.aspire.thi.domain.LineItemLog;
import com.aspire.thi.domain.LineItemWeight;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.Weitage;

public interface ThiScoreRepository {

	public List<AssesmentGroupScore> getAssesmentGroupScores(Integer thiScoreId);

	public List<LineItemLog> getLineItemLogs(Integer assesmentGroupScoreId);

	public ThiScore getThiScore(Integer projectId, Date auditCycleDate);

	public ThiScore getThiAudit(Integer projectId, Date auditCycleDate, Integer auditorId, Integer assignmentTypeId);

	public List<AssesmentGroupScore> getAssesmentGroups(Integer assignmentTypeId);

	public List<LineItemLog> getAssesmentLineItems(Integer assignmentType, Integer assesmentGroupId);

	public ThiScore getAuditScore(Integer assignmentType);

	public void updateThiScore(ThiScore score);

	public void insertThiScore(ThiScore score);
	
	public ProjectAuditor getProjectAuditor(Integer projectId, Date auditCycleDate);

	public List<AssesmentType> getAssesmentTypes();

	public List<String> getAssesmentCriteria(Integer assesmentGroupId);

	public void setAuditStatus(Boolean auditComplete, Integer projectId, Integer auditorId, Date auditCycleDate);

	public String getProjectOwnerId(Integer projectId);

	public String getAssessmentGroupName(Integer assesmentGroupId);
	
	public void insertAuditee(int projectId,String[] auditee,String userAceNo);
	
	public List<String> getProjectAuditee(int projectId);

	/* to read weitage from db
	public List<LineItemWeight> getAssessmentScore(Integer assessmentType);
	
	public List<LineItemWeight> getAssessmentLists(Integer id,String groupName,Integer assessmentType);
	//setting
	public int updateLineItem(int id,String groupName,Integer assessmentType,Integer percentage); */
}