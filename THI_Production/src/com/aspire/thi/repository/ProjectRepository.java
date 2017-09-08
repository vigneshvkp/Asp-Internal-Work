package com.aspire.thi.repository;

import java.util.List;
import java.util.Map;

import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProsProject;
import com.aspire.thi.domain.ProjectAudit;

public interface ProjectRepository {

	public void saveProsProject(ProsProject prosProject);

/*	public List<ProsProject> getAllProsProjects();

	public ProsProject getProsProject(int id);
*/
	public List<Project> getProjects(List<Integer> projectIds);

	/*Get project with audit date*/
	public List<ProjectAudit> getProjectsWithAuditDate(List<Integer> projectIds);
	
	public List<Project> getAllProjects();
	
	public List<Project> getProjectList();
	
	public void updateProjectAuditFrequency(Project proj);
	
	public Project getProjectByID(int id);
	
/*	public ProsProject getProsProjectById(int id);
	
	public List<ProsProject> getAllProsProjs();
*/	
	public List<Integer> getAssociatedProjectIds(String userId);

	public void syncProsProject(ProsProject prosProjects);
	
	public List<Project> searchProjects(Map<String, String> searchKeys);
	
	//To get the previous auditor of the project
	public String getProjectPreviousAuditorName(Integer prevAuditorId);
	
	public Integer getProjectPreviousAuditorId(Integer projectId);
	
	public String getProjectAuditScheduler(Integer projectId);
	
}
