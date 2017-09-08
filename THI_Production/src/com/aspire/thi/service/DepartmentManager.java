package com.aspire.thi.service;

import java.util.List;

import com.aspire.thi.domain.Department;
import com.aspire.thi.repository.DepartmentRepository;

public class DepartmentManager implements IDepartmentManager{

	private DepartmentRepository departmentDao;

	@Override
	public void saveDepartment(Department department) {
		departmentDao.saveDepartment(department);
	}

	@Override
	public void saveAllDepartments(List<Department> departments) {
		for (Department department : departments) {
			saveDepartment(department);
		}
	}

	@Override
	public Department getDepartment(int id) {
		return departmentDao.getDepartment(id);
	}

	@Override
	public List<Department> getAllDepartments() {
		return departmentDao.getAllDepartments();
	}
	
	public void setDepartmentDao(DepartmentRepository departmentDao){
		this.departmentDao = departmentDao;
		
	}

	@Override
	public List<Department> getDelivaryUnits() {
		return departmentDao.getDeliveryUnits();
	}

}
