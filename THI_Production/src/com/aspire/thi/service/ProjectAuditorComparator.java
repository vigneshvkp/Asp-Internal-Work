package com.aspire.thi.service;

import java.util.Comparator;

public class ProjectAuditorComparator implements Comparator<ProjectAuditorHelper>{

	private static ProjectAuditorComparator INSTANCE = new ProjectAuditorComparator();
	private String sortByName;
	private String sortDirection;

	private ProjectAuditorComparator() {
		super();
	}
	
	public static ProjectAuditorComparator getInstance() {
		return INSTANCE;
	}

	@Override
	public int compare(ProjectAuditorHelper o1, ProjectAuditorHelper o2) {
		if(sortByName.equals("THIName")) {
			if(sortDirection.equals("desc")) {
				return o2.getProjectName().compareToIgnoreCase(o1.getProjectName());
			} else {
				return o1.getProjectName().compareToIgnoreCase(o2.getProjectName());
			}
		} else {
			if(sortDirection.equals("desc")) {
				return o2.getAuditorName().compareToIgnoreCase(o1.getAuditorName());
			} else {
				return o1.getAuditorName().compareToIgnoreCase(o2.getAuditorName());
			}			
		}
	}

	public void setSortByName(String sortByName) {
		this.sortByName = sortByName;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}


}
