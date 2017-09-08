package com.aspire.thi.service;

import java.util.List;

import com.aspire.thi.domain.Department;

public interface IDepartmentManager {

	public void saveDepartment(Department customer);

	public void saveAllDepartments(List<Department> customers);
	
	public Department getDepartment(int id);
	
	public List<Department> getAllDepartments();
	
	public List<Department> getDelivaryUnits();

}
