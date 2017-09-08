package com.aspire.thi.service;

import java.util.Comparator;

import com.aspire.thi.domain.ProsProject;

public class ProsProjectComparator implements Comparator<ProsProject>{
	
	@Override
	public int compare(ProsProject o1, ProsProject o2) {
		return o1.getProjectName().compareToIgnoreCase(o2.getProjectName());
	}

}
