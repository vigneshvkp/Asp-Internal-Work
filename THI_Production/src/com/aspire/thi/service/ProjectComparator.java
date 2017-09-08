package com.aspire.thi.service;

import java.util.Comparator;

import com.aspire.thi.domain.Project;

public class ProjectComparator implements Comparator<Project> {
	
	private static ProjectComparator INSTANCE = new ProjectComparator();
	private String sortDirection;

	private ProjectComparator() {
		super();
	}
	
	public static ProjectComparator getInstance() {
		return INSTANCE;
	}
	@Override
	public int compare(Project o1, Project o2) {
		if(sortDirection.equals("desc")) {
			return o2.getProjectName().compareToIgnoreCase(o1.getProjectName());
		} else {
			return o1.getProjectName().compareToIgnoreCase(o2.getProjectName());
		}			
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

}
