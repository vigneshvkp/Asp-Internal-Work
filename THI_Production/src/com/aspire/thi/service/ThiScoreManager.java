package com.aspire.thi.service;

import java.util.Date;
import java.util.List;

import com.aspire.thi.domain.AssesmentType;
import com.aspire.thi.domain.LineItemWeight;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.Weitage;
import com.aspire.thi.repository.ThiScoreRepository;


@SuppressWarnings("serial")
public class ThiScoreManager implements ThiManager {

	private ThiScoreRepository thiScoreDao;

	@Override
	public ThiScore getThiScore(Integer projectId, Date auditCycleDate) {
		return thiScoreDao.getThiScore(projectId, auditCycleDate);
	}

	public void setThiScoreDao(ThiScoreRepository thiScoreDao) {
		this.thiScoreDao = thiScoreDao;
	}

	public ThiScoreRepository getThiScoreDao() {
		return thiScoreDao;
	}

	@Override
	public ProjectAuditor getProjectAuditor(Integer projectId, Date auditCycleDate) {
		return thiScoreDao.getProjectAuditor(projectId, auditCycleDate);
	}

	@Override
	public ThiScore createNewAudit(Integer assignmentType) {
		return thiScoreDao.getAuditScore(assignmentType);
	}

	@Override
	public void updateAudit(ThiScore score) {
		thiScoreDao.updateThiScore(score);
	}

	@Override
	public void saveAudit(ThiScore score) {
		thiScoreDao.insertThiScore(score);
	}

	@Override
	public List<AssesmentType> getAssesmentTypes() {
		return thiScoreDao.getAssesmentTypes();
	}

	@Override
	public List<String> getAssesmentCriteria(Integer assesmentGroupId) {
		return thiScoreDao.getAssesmentCriteria(assesmentGroupId);
	}

	@Override
	public void setAuditComplete(Boolean auditComplete, Integer projectId, Integer auditorId, Date auditCycleDate) {
		this.thiScoreDao.setAuditStatus(auditComplete, projectId, auditorId, auditCycleDate);
	}
	
	@Override
	public void insertAuditee(int projectId,String[] auditee,String userAceNo){
		this.thiScoreDao.insertAuditee(projectId, auditee,userAceNo);
	}
	
	@Override
	public List<String> getProjectAuditee(int projectId){
		return this.thiScoreDao.getProjectAuditee(projectId);
	}
	

	@Override
	public String getAssessmentGroupName(Integer assessmentGroupId) {
		return thiScoreDao.getAssessmentGroupName(assessmentGroupId);
	}

	//vkp
	@Override
	public List<LineItemWeight> getLineItemWeitage(int assesmentType) {
		return this.thiScoreDao.getAssessmentScore(assesmentType);
	}

	@Override
	public List<LineItemWeight> getLineItems(int id, String groupName, int assessmentType) {
		return this.thiScoreDao.getAssessmentLists(id, groupName, assessmentType);
	}

	@Override
	public int updateLineItem(int id, String groupName, int assessId, int percentage) {
		// TODO Auto-generated method stub
		return this.thiScoreDao.updateLineItem(id, groupName, assessId, percentage);
	}
	

}