package com.aspire.thi.service;

import java.util.List;
import java.util.Map;

import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProjectAudit;
import com.aspire.thi.domain.ProsProject;

public interface IProjectManager {
	
	public void saveAllProsProjects(List<ProsProject> prosProjects);
	
	public void saveProsProject(ProsProject prosProject);

//	public List<ProsProject> getAllProsProjects();

	public List<Project> getProjects(List<Integer> projectIds);

	public List<Project> getAllProjects();
		
//	public ProsProject getProsProject(int id);

	public List<Project> getProjectList();
	
	public void saveProject(Project proj);
	
	public Project getProjectByID(int id);

//	public ProsProject getProsProjectById(int id);
//	
//	public List<ProsProject> getAllProsProjs();

	public List<Integer> getAssociatedProjectIds(String userId);

	public void sycnProsProjects(List<ProsProject> prosProjects);
	
	public List<Project> searchProjects(Map<String, String> searchKeys);
	
	public List<ProjectAudit> getProjectsWithAuditDate(List<Integer> projectIds);
	
	public String getProjectPreviousAuditorName(Integer projectId);
	
	public String getProjectAuditScheduler(Integer projectId);
}
