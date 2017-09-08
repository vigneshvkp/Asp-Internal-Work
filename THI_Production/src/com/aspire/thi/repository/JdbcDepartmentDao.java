package com.aspire.thi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.aspire.thi.domain.Department;

public class JdbcDepartmentDao extends SimpleJdbcDaoSupport implements
		DepartmentRepository {

	/** Logger for this class and subclasses */
	protected static final Log LOGGER = LogFactory.getLog(JdbcDepartmentDao.class);

	private static final String GET_ALL_DEPARTMENTS = "SELECT id, pros_dept_id, name, dept_head_ace_no, created_by, created_on, last_modified_by, last_modified_on FROM department ";

	private static final String GET_DEPARTMENT_WITH_ID = GET_ALL_DEPARTMENTS
			+ " WHERE id = :id";

	private static final String GET_DELIVERY_UNITS = "SELECT DISTINCT department.id, pros_dept_id, NAME, dept_head_ace_no, "
			+ " department.created_by, department.created_on, department.last_modified_by, department.last_modified_on "
			+ " FROM department INNER JOIN project ON department.id = project.dept_id ORDER BY NAME";

	private static final String IS_DEPT_AVBL_FOR_DEPT_ID = "SELECT COUNT(id) FROM department"
		+ " WHERE pros_dept_id = :pros_dept_id";

	private static final String UPDATE_DEPT = "UPDATE department SET dept_head_ace_no = :dept_head_ace_no, name = :name, " +
			"last_modified_by = :last_modified_by, last_modified_on = :last_modified_on " +
			" WHERE id = :id";

	@Override
	public List<Department> getAllDepartments() {
		logger.info("Getting ALL Departments!");
		List<Department> departments = getSimpleJdbcTemplate().query(
				GET_ALL_DEPARTMENTS, new DepartmentMapper());
		return departments;
	}
	
	@Override
	public Department getDepartment(int id) {
		logger.info("Getting Department for id = " + id);

		Department department = getSimpleJdbcTemplate().queryForObject(
				GET_DEPARTMENT_WITH_ID, new DepartmentMapper(),
				new MapSqlParameterSource().addValue("id", id));

		return department;

	}

	@Override
	public void saveDepartment(Department department) {
		logger.info("Updating Department back to database ");
		
		int count = getSimpleJdbcTemplate().queryForInt(
				IS_DEPT_AVBL_FOR_DEPT_ID, new MapSqlParameterSource().addValue("pros_dept_id", department.getProsDeptId()));

		if (count == 0) {
			SimpleJdbcInsert insertDepartment= new SimpleJdbcInsert(getDataSource()).withTableName("department");
			Map<String, Object> parameters = new HashMap<String, Object>(8);
			parameters.put("id", department.getId());
			parameters.put("pros_dept_id", department.getProsDeptId());
			parameters.put("name", department.getName());
		    parameters.put("dept_head_ace_no", department.getDeptHeadAceNo());
		    parameters.put("created_by", department.getCreatedBy());
		    parameters.put("created_on", department.getCreatedOn());
		    parameters.put("last_modified_by", department.getLastModifiedBy());
		    parameters.put("last_modified_on", department.getLastModifiedOn());
		    int value = insertDepartment.execute(parameters);
		    LOGGER.debug("*******************************Department got executed with id "+value +"****************************");
		} else {
			int updRows = getSimpleJdbcTemplate().update(
					UPDATE_DEPT, new MapSqlParameterSource()
							.addValue("dept_head_ace_no", department.getDeptHeadAceNo())
							.addValue("name", department.getName())
							.addValue("last_modified_by", department.getLastModifiedBy())
							.addValue("last_modified_on", department.getLastModifiedOn())
							.addValue("id", department.getId()));
			LOGGER.info(department + " already avbl in Database, so updating = " + updRows);
		}
		logger.info("Updation completed for department with id " + department.getId());
	}
	
	private static class DepartmentMapper implements RowMapper<Department> {

		@Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			Department department = new Department();
			
			department.setId(rs.getInt("id"));
			department.setProsDeptId(rs.getString("pros_dept_id"));
			department.setName(rs.getString("name"));
			department.setDeptHeadAceNo(rs.getString("dept_head_ace_no"));
			department.setCreatedBy(rs.getString("created_by"));
			department.setCreatedOn(rs.getDate("created_on"));
			department.setLastModifiedBy(rs.getString("last_modified_by"));
			department.setLastModifiedOn(rs.getDate("last_modified_on"));
			
			return department;
		}
		
	}

	@Override
	public List<Department> getDeliveryUnits() {
		logger.info("Getting ALL Delivery units!");
		List<Department> departments = getSimpleJdbcTemplate().query(GET_DELIVERY_UNITS, new DepartmentMapper());
		return departments;
	}

}