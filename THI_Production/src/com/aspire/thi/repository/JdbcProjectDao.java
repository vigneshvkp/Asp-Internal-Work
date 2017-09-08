package com.aspire.thi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProsProject;
import com.aspire.thi.domain.ProjectAudit;
import com.aspire.thi.domain.ProjectAuditor;

@SuppressWarnings("deprecation")
public class JdbcProjectDao extends SimpleJdbcDaoSupport implements
		ProjectRepository {

	/** Logger for this class and subclasses */
	protected static final Logger LOGGER = Logger.getLogger(JdbcProjectDao.class);

/*	private static final String GET_PROSPROJECT_LIST = "SELECT id, pros_prj_identifier, project_name, customer_id, " +
			"dept_id, active, owner_user_id, start_date, end_date, created_by, created_on, last_modified_by, last_modified_on  " +
			"FROM pros_project order by project_name";

	private static final String GET_PROS_PROJECT_BY_ID = "SELECT id, pros_prj_identifier, project_name, customer_id, " +
			"dept_id, active, owner_user_id, start_date, end_date, created_by, created_on, last_modified_by, last_modified_on  " +
			"FROM pros_project WHERE id = :id";
*/
	private static final String GET_PROJECT_LIST = "SELECT id, pros_project_id, prj_name, dept_id, active, " +
			"start_date, end_date, created_by, created_on, " +
			"last_modified_by, last_modified_on, audit_freq, customer_id, dept_id, owner_user_id FROM project ";

	private static final String GET_PROJECT_BY_ID = "SELECT id, pros_project_id, prj_name, active, start_date, end_date, " +
			" p.created_by, p.created_on, p.last_modified_by, p.last_modified_on, p.audit_freq, customer_id, dept_id, owner_user_id " +
			" FROM project p WHERE p.id = :id ";

	private static final String UPDATE_PROJECT_BY_ID = "UPDATE project SET last_modified_by = :last_modified_by, last_modified_on = " +
			":last_modified_on, audit_freq= :audit_freq WHERE id = :id";
	
	private static final String INSERT_PROJECT = "INSERT INTO project(pros_project_id, prj_name, active, start_date, " +
			"end_date, created_by, created_on, last_modified_by, last_modified_on, audit_freq) VALUES(?,?,?,?,?,?,?,?,?,?)";

	private static final String IS_PROJECT_AVBL_FOR_PROS_PRJ_ID = "SELECT COUNT(id) FROM project"
		+ " WHERE pros_project_id = :pros_project_id";

	private static final String UPDATE_PROJECT_BY_PROS_PROJECT_ID = "UPDATE project SET active = :active, " +
			" prj_name = :project_name, owner_user_id= :owner_user_id, dept_id = :dept_id, " +
			" end_date = :end_date, last_modified_by = :last_modified_by, last_modified_on = :last_modified_on " +
			" WHERE pros_project_id = :pros_project_id";

/*	@Override
	public List<ProsProject> getAllProsProjects() {
		LOGGER.info("Getting ALL Pros Projects!");
		List<ProsProject> prosProjects = getSimpleJdbcTemplate().query(
				GET_PROSPROJECT_LIST, new ProsProjMapper());
		return prosProjects;
	}
*/
/*	@Override
	public ProsProject getProsProject(int id) {
		LOGGER.info("Getting Pros Project for id = " + id);

		ProsProject prosProject = getSimpleJdbcTemplate().queryForObject(
				GET_PROS_PROJECT_BY_ID, new ProsProjMapper(),
				new MapSqlParameterSource().addValue("id", id));

		return prosProject;

	}
*/
	@Override
	public List<Project> getProjectList() {
		LOGGER.info("Getting ALL Projects!");
		List<Project> projects = getSimpleJdbcTemplate().query(
				GET_PROJECT_LIST, new ProjectMapper());
		return projects;
	}

	@Override
	public Project getProjectByID(int id) {
		Project project = getSimpleJdbcTemplate().queryForObject(
				GET_PROJECT_BY_ID, new ProjectMapper(),
				new MapSqlParameterSource().addValue("id", id));
		return project;
	}

	@Override
	public List<Project> searchProjects(Map<String, String> searchKeys) {
		
		String thiNameStr = searchKeys.get("thiNameStr");
		String deptStr = searchKeys.get("deptStr");
		
		StringBuilder sql = new StringBuilder(GET_PROJECT_LIST);
		StringBuilder params = new StringBuilder();
		if(thiNameStr != null && thiNameStr.trim().length() > 0) {
			params.append(" prj_name LIKE '%");
			params.append(thiNameStr);
			params.append("%'");
			if(deptStr != null && deptStr.trim().length() > 0 && Integer.valueOf(deptStr) > 0) {
				params.append(" AND ");
			}
		}
		if(deptStr != null && deptStr.trim().length() > 0 && Integer.valueOf(deptStr) > 0) {
			params.append(" dept_id = ");
			params.append(deptStr);
		}
		
		if (params.length() > 0) {
			sql.append(" WHERE ");
			sql.append(params.toString());
		}
		
		LOGGER.debug("searchProjects Query -->" + sql.toString() );
		List<Project> projects = getSimpleJdbcTemplate().query(
				sql.toString(), new ProjectMapper());
		return projects;		
	}

	@Override
	public void updateProjectAuditFrequency(Project proj) {
		
		int count = 0;
		LOGGER.info("Saving Project for " + proj );

		if (proj.getId() > 0) {
			count = getSimpleJdbcTemplate().update(
					UPDATE_PROJECT_BY_ID,
					new MapSqlParameterSource()
							.addValue("last_modified_by",
									proj.getLastModifiedBy())
							.addValue("last_modified_on",
									proj.getLastModifiedOn())
							.addValue("audit_freq", proj.getAuditFrequency())
							.addValue("id", proj.getId()));
							
		} else {
			count = getSimpleJdbcTemplate()
					.update(INSERT_PROJECT,
							new Object[] { proj.getProsProjectId(),
									proj.getProjectName(), proj.isActive(),
									proj.getStartDate(), proj.getEndDate(),
									proj.getCreatedBy(), proj.getCreatedOn(),
									proj.getLastModifiedBy(),
									proj.getLastModifiedOn(),
									proj.getAuditFrequency()});

		}

		LOGGER.debug("Rows updated: " + count);
	}

/*	@Override
	public List<ProsProject> getAllProsProjs() {
		LOGGER.debug("Getting ALL Pros Projects!");
		List<ProsProject> prosProjects = getSimpleJdbcTemplate().query(
				GET_PROSPROJECT_LIST, new ProsProjMapper());
		return prosProjects;
	}
*/
/*	@Override
	public ProsProject getProsProjectById(int id) {
		LOGGER.debug("Getting Pros Project for id = " + id);

		ProsProject prosProject = getSimpleJdbcTemplate().queryForObject(
				GET_PROS_PROJECT_BY_ID, new ProsProjMapper(),
				new MapSqlParameterSource().addValue("id", id));

		return prosProject;

	}
*/
	@Override
	public void saveProsProject(ProsProject prosProject) {
		LOGGER.info("Updating ProsProject back to database ");

		SimpleJdbcInsert insertProsProject = new SimpleJdbcInsert(
				getDataSource()).withTableName("project");
		Map<String, Object> parameters = new HashMap<String, Object>(12);
		parameters.put("id", prosProject.getId());
		parameters.put("pros_project_id", prosProject.getProsProjectId());
		parameters.put("prj_name", prosProject.getProjectName());
		parameters.put("customer_id", prosProject.getCustomerId());
		parameters.put("dept_id", prosProject.getDeptId());
		parameters.put("active", prosProject.isActive());
		parameters.put("owner_user_id", prosProject.getOwnerUserId());
		parameters.put("start_date", prosProject.getStartDate());
		parameters.put("end_date", prosProject.getEndDate());
		parameters.put("created_by", prosProject.getCreatedBy());
		parameters.put("created_on", prosProject.getCreatedOn());
		parameters.put("last_modified_by", prosProject.getLastModifiedBy());
		parameters.put("last_modified_on", prosProject.getLastModifiedOn());
		parameters.put("audit_freq", 2); // Default audit frequency - 2 months
		int value = insertProsProject.execute(parameters);// .intValue();
		LOGGER.info("*******************************Pros Project got executed with ID "
				+ value + "****************************");
		// customer.setId(value);
		// } else {

		// }
		logger.debug("Updating  Pros Project for customer with id "
				+ prosProject.getId());
	}
	
	@Override
	public void syncProsProject(ProsProject prosProject) {
		LOGGER.info("Synchronization  of ProsProject into database "
				+ prosProject);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pros_project_id", prosProject.getProsProjectId());
		int count = getSimpleJdbcTemplate().queryForInt(
				IS_PROJECT_AVBL_FOR_PROS_PRJ_ID, params);
		if (count == 0) {
			saveProsProject(prosProject);
		} else {
			int updRows = getSimpleJdbcTemplate().update(
					UPDATE_PROJECT_BY_PROS_PROJECT_ID,
					new MapSqlParameterSource()
							.addValue("active", prosProject.isActive())
							.addValue("project_name", prosProject.getProjectName())
							.addValue("owner_user_id", prosProject.getOwnerUserId())
							.addValue("end_date", prosProject.getEndDate())
							.addValue("last_modified_by",
									prosProject.getLastModifiedBy())
							.addValue("last_modified_on",
									prosProject.getLastModifiedOn())
							.addValue("dept_id", prosProject.getDeptId())
							.addValue("pros_project_id",
									prosProject.getProsProjectId()));
			LOGGER.info(prosProject
					+ " already avbl in Database, so updating = " + updRows);
		}

	}
		
/*	private static class ProsProjMapper implements ParameterizedRowMapper<ProsProject> {

		@Override
		public ProsProject mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProsProject prosProject = new ProsProject();

			prosProject.setId(rs.getInt("id"));
			prosProject.setProsProjectId(rs.getInt("pros_prj_identifier"));
			prosProject.setProjectName(rs.getString("project_name"));
			prosProject.setCustomerId(rs.getInt("customer_id"));
			prosProject.setDeptId(rs.getInt("dept_id"));
			prosProject.setActive(rs.getBoolean("active"));
			prosProject.setOwnerUserId(rs.getString("owner_user_id"));
			prosProject.setStartDate(rs.getDate("start_date"));
			prosProject.setEndDate(rs.getDate("end_date"));
			prosProject.setCreatedBy(rs.getString("created_by"));
			prosProject.setCreatedOn(rs.getDate("created_on"));
			prosProject.setLastModifiedBy(rs.getString("last_modified_by"));
			prosProject.setLastModifiedOn(rs.getDate("last_modified_on"));

			return prosProject;
		}

	}
*/
	private static class ProjectMapper implements ParameterizedRowMapper<Project> {
		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();

			project.setId(rs.getInt("id"));
			project.setProsProjectId(rs.getInt("pros_project_id"));
			project.setProjectName(rs.getString("prj_name"));
			project.setCustomerId(rs.getInt("customer_id"));
			project.setDeptId(rs.getInt("dept_id"));
			project.setActive(rs.getBoolean("active"));
			project.setOwnerUserId(rs.getString("owner_user_id"));
			project.setStartDate(rs.getDate("start_date"));
			project.setEndDate(rs.getDate("end_date"));
			project.setAuditFrequency(rs.getInt("audit_freq"));
			return project;
		}

	}

	private static class ProjMapper implements ParameterizedRowMapper<Project> {
		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setId(rs.getInt("id"));
			String projectName = rs.getString("prj_name");
			project.setName(projectName);
			project.setProjectName(projectName);
			return project;
		}

	}
	private static class ProjectAuditDateMapper implements ParameterizedRowMapper<ProjectAudit> {
		@Override
		public ProjectAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			ProjectAudit project = new ProjectAudit();
			project.setId(rs.getInt("id"));
			String projectName = rs.getString("prj_name");
			int audit_freq = rs.getInt("audit_freq");
			Date auditDate = rs.getDate("audit_date");
			project.setName(projectName);
			project.setProjectName(projectName);
			project.setAuditFrequency(audit_freq);
			ProjectAuditor projectAuditor = new ProjectAuditor();
			projectAuditor.setAuditDate(auditDate);
			project.setProjectAuditor(projectAuditor);
			return project;
		}

	}
	private static class ProjIdMapper implements ParameterizedRowMapper<Integer> {
		
		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt("id");
		}

	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projects = getSimpleJdbcTemplate().query(
				" SELECT id, prj_name FROM project order by prj_name", new ProjMapper());
		return projects;
	}

	@Override
	public List<Project> getProjects(List<Integer> projectIds) {
		String query = "SELECT id, prj_name FROM project WHERE %s order by prj_name";
		String booleanQuery = StringUtils.collectionToDelimitedString(
				projectIds, " OR ", "id='", "'");
		query = String.format(query, booleanQuery);
		System.out.println("boolquery:"+query);
		LOGGER.debug("*******************************Projects Query =" + query + "****************************");
		List<Project> projects = getSimpleJdbcTemplate().query(query,
				new ProjMapper());
		return projects;
	}

	@Override
	public List<Integer> getAssociatedProjectIds(String userId) {
		String query = "SELECT id FROM project WHERE owner_user_id = :owner_user_id";
		List<Integer> projectIds = getSimpleJdbcTemplate().query(query, new ProjIdMapper(),
											new MapSqlParameterSource().addValue("owner_user_id", userId));
		return projectIds;

		/*//		StringBuilder projects = new StringBuilder("'-1'");
//		for (Iterator<String> iterator = proProjectNames.iterator(); iterator.hasNext();) {
//			String project = iterator.next();
//			projects.append(",'").append(project).append("'");
//		}
//		String query = "Select id from project where pros_project_id in (SELECT id FROM pros_project where project_name in ("
//				+ projects.toString() + "))";
		String query = "SELECT id FROM project WHERE pros_project_id IN (SELECT id FROM pros_project WHERE %s )";
		String booleanQuery = StringUtils.collectionToDelimitedString(
				proProjectNames, " OR ", "project_name='", "'");
		LOGGER.debug("*******************************booleanQuery =" + booleanQuery + "****************************");
		
		// Muthu [ This may seem stupid but don't remove this check
		if(booleanQuery.length() == 0) 
			booleanQuery = " 1 = 2 "; // to set a false condition;
		//]Muthu
		
		query = String.format(query, booleanQuery);
		LOGGER.debug("*******************************query =" + query + "****************************");
		List<Integer> projectIds = getSimpleJdbcTemplate().query(query, new ProjIdMapper());
		return projectIds;
*/	}
	
	/*Get project with audit date*/
	@Override
	public List<ProjectAudit> getProjectsWithAuditDate(List<Integer> projectIds){
		StringBuffer sb = new StringBuffer(6400);
		sb.append("SELECT distinct pro.id,pro.prj_name,pro.audit_freq,pam.audit_date FROM project pro");
		sb.append(" inner join project_auditor_mapping pam on pam.project_id = pro.id");
		sb.append(" WHERE pam.audit_complete = 0 and (%s) order by prj_name");
		String booleanQuery = StringUtils.collectionToDelimitedString(
				projectIds, " OR ", "pro.id='", "'");
		String query = String.format(sb.toString(), booleanQuery);
		System.out.println("boolquery:"+query);
		LOGGER.debug("*******************************Projects Query =" + query + "****************************");
		List<ProjectAudit> projects = getSimpleJdbcTemplate().query(query,
				new ProjectAuditDateMapper());
		return projects;
	}
	
	public Integer getProjectPreviousAuditorId(Integer projectId){
		StringBuffer sb1 = new StringBuffer(3200);
		sb1.append("select auditor_id from project_auditor_history");
		sb1.append(" where project_id=?");
		sb1.append(" group by auditor_id order by created_on desc limit 1");
		
		//check for the availability of data in project_auditor_history table
		StringBuffer sb2 = new StringBuffer(3200);
		sb2.append("select count(auditor_id) from project_auditor_history");
		sb2.append(" where project_id=?");
		
		StringBuffer sb3 = new StringBuffer(3200);
		sb3.append("select auditor_id from project_auditor_mapping");
		sb3.append(" where project_id=?");
		sb3.append(" order by created_on desc limit 1");
		
		//check for the availability of data in project_auditor_mapping table
		StringBuffer sb4 = new StringBuffer(3200);
		sb4.append("select count(auditor_id) from project_auditor_mapping");
		sb4.append(" where project_id=?");
		
		Integer auditorCount = getSimpleJdbcTemplate()
		.queryForObject(sb2.toString(),Integer.class,projectId );
		if(auditorCount != 0){
			Integer auditorId = getSimpleJdbcTemplate()
			.queryForObject(sb1.toString(),Integer.class,projectId );
			return auditorId;
		}else{
			auditorCount = getSimpleJdbcTemplate()
			.queryForObject(sb4.toString(),Integer.class,projectId );
			if(auditorCount != 0){
				Integer auditorId = getSimpleJdbcTemplate()
				.queryForObject(sb3.toString(),Integer.class,projectId );
				return auditorId;
			}else{
				return 0;		//no data available at both project_auditor_mapping and project_auditor_history
			}
		}
	}
	
	/*Get project previous auditor id*/
	public String getProjectPreviousAuditorName(Integer prevAuditorId){
		StringBuffer sb = new StringBuffer(6400);
		sb.append("select name from auditor where id =?");
		
		String prevAuditorName = getSimpleJdbcTemplate()
		.queryForObject(sb.toString(),String.class, prevAuditorId);
		return prevAuditorName;
	}
	
	public String getProjectAuditScheduler(Integer projectId){
		StringBuffer sb = new StringBuffer(6400);
		sb.append("select last_modified_by from project_auditor_mapping");
		sb.append(" where id in(select max(id) from project_auditor_mapping");
		sb.append(" where project_id="+projectId+")");
		
		String projectAuditScheduler = getSimpleJdbcTemplate()
		.queryForObject(sb.toString(),String.class);
		return projectAuditScheduler;
	}
	

}