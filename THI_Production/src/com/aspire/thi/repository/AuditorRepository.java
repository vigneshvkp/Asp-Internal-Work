package com.aspire.thi.repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.service.ProjectAuditorHelper;

public interface AuditorRepository {
	
	public List<Auditor> getAuditorList();
	
	public Auditor getAuditorByID(int id);
	
	public boolean isAuditor(String aceNo);
	
	public int getAuditorId(String aceNo);

	public List<Integer> getAssociatedProjects(int auditorId);
	
	public List<ProjectAuditor> getProjectAuditorList();
	
	public ProjectAuditor getProjectAuditorByID(int id) throws SQLException;
	
	public boolean saveProjectAuditor(ProjectAuditor projAuditor) throws SQLException;
	
	public List<ProjectAuditorHelper> searchProjectAuditors(String thiName, Date startDate, Date endDate, boolean auditComplete);
	
	public boolean createAuditor(Auditor auditor);
	
	public List<ProjectAuditorHelper> getProjectAuditorsByProjectId(String projectId);
	
	public int getProjectAuditorId(int projectId);
	
	public String removeAuditor(String aceNo, String lastModifiedBy);

}
