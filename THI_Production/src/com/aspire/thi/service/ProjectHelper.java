package com.aspire.thi.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.aspire.thi.domain.Project;

public class ProjectHelper {

    /** Logger for this class and subclasses */
    private static final Logger LOGGER = Logger.getLogger(ProjectHelper.class);
    
    private int id;
    private int prosProjectId;
    private String prosProjectName;
    private String projectName;
    private boolean active;
    private Date startDate;
    private Date endDate;
    
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
		LOGGER.debug("ProjectName set to: " + projectName);
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
		LOGGER.debug("Active set to: " + active);
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		LOGGER.debug("StartDate set to: " + startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		LOGGER.debug("EndDate set to: " + endDate);
	}
	public int getProsProjectId() {
		return prosProjectId;
	}
	public void setProsProjectId(int prosProjectId) {
		this.prosProjectId = prosProjectId;
	}
	public String getProsProjectName() {
		return prosProjectName;
	}
	public void setProsProjectName(String prosProjectName) {
		this.prosProjectName = prosProjectName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public static ProjectHelper convertToAddProject(Project proj) {
    	ProjectHelper project = new ProjectHelper();
    	project.setId(proj.getId());
    	project.setProsProjectId(proj.getProsProjectId());
		project.setProjectName(proj.getProjectName());
		project.setActive(proj.isActive());
		project.setStartDate(proj.getStartDate());
		project.setEndDate(proj.getEndDate());
		return project;
	}
}
