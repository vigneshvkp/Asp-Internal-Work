package com.aspire.thi.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProjectAudit;
import com.aspire.thi.domain.ProsProject;
import com.aspire.thi.repository.ProjectRepository;

public class ProjectManager implements IProjectManager {

	private ProjectRepository projectDao;
	
	public void setProjectDao(ProjectRepository projectDao){
		this.projectDao = projectDao;
	}
	
	@Override
	public void saveAllProsProjects(List<ProsProject> prosProjects) {
		// TODO Auto-generated method stub
		Iterator<ProsProject> iterator = prosProjects.iterator();
		for (; iterator.hasNext();) {
			saveProsProject(iterator.next());
		}
		
	}

/*	@Override
	public List<ProsProject> getAllProsProjects() {
		return projectDao.getAllProsProjects();
	}
*/
	@Override
	public void saveProsProject(ProsProject prosProject) {
		projectDao.saveProsProject(prosProject);
		
	}

	@Override
	public List<Project> getProjects(List<Integer> projectIds) {
		return projectDao.getProjects(projectIds);
	}

	@Override
	public List<Project> getAllProjects() {
		return projectDao.getAllProjects();
	}

	@Override
	public Project getProjectByID(int id) {
		return projectDao.getProjectByID(id);
	}

	@Override
	public List<Project> getProjectList() {
		return projectDao.getProjectList();
	}

/*	@Override
	public ProsProject getProsProject(int id) {
		return projectDao.getProsProject(id);
	}
*/
	@Override
	public void saveProject(Project proj) {
		projectDao.updateProjectAuditFrequency(proj);		
	}

/*	@Override
	public List<ProsProject> getAllProsProjs() {
		return projectDao.getAllProsProjs();
	}

	@Override
	public ProsProject getProsProjectById(int id) {
		return projectDao.getProsProjectById(id);
	}
*/
	@Override
	public List<Integer> getAssociatedProjectIds(String userId) {
		return projectDao.getAssociatedProjectIds(userId);
	}

	@Override
	public void sycnProsProjects(List<ProsProject> prosProjects) {
		for(ProsProject prosProject : prosProjects) {
			projectDao.syncProsProject(prosProject);
		}
	}
	
	@Override
	public List<Project> searchProjects(Map<String, String> searchKeys) {
		return projectDao.searchProjects(searchKeys);
	}
	
	public List<ProjectAudit> getProjectsWithAuditDate(List<Integer> projectIds){
		return projectDao.getProjectsWithAuditDate(projectIds);
	}
	
	public String getProjectPreviousAuditorName(Integer prevAuditorId){
		return projectDao.getProjectPreviousAuditorName(prevAuditorId);
	}
	
	public Integer getProjectPreviousAuditorId(Integer projectId){
		return projectDao.getProjectPreviousAuditorId(projectId);
	}
	
	public String getProjectAuditScheduler(Integer projectId){
		return projectDao.getProjectAuditScheduler(projectId);
	}
	
		
}
