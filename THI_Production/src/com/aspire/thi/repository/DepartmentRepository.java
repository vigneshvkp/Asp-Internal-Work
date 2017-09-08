package com.aspire.thi.repository;

import java.util.List;

import com.aspire.thi.domain.Department;

public interface DepartmentRepository {

    public List<Department> getAllDepartments();

	public Department getDepartment(int id);

	public void saveDepartment(Department department);

	public List<Department> getDeliveryUnits();

}