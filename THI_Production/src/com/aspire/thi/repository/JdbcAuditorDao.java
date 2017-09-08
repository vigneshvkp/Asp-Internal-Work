package com.aspire.thi.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.service.ProjectAuditorHelper;

@SuppressWarnings("deprecation")
public class JdbcAuditorDao extends SimpleJdbcDaoSupport implements AuditorRepository{

	/** Logger for this class and subclasses */
	private static final Logger LOGGER = Logger.getLogger(JdbcAuditorDao.class);

	private static final String GET_AUDITOR_LIST = "SELECT id, ace_no, name, active, dept_id, dept_name, " +
			"email, created_by, created_on, last_modified_by, last_modified_on FROM auditor " +
			" where active = 1 order by name";

	private static final String GET_AUDITOR_BY_ID = "SELECT id, ace_no, name, active, dept_id, dept_name, " +
	"email, created_by, created_on, last_modified_by, last_modified_on FROM auditor  WHERE id = :id";

	private static final String IS_AUDITOR = "SELECT count(id) FROM auditor WHERE ace_no=:aceNo and active=1";

	private static final String GET_AUDITOR_ID = "SELECT id FROM auditor WHERE ace_no=:aceNo and active=1";

	private static final String GET_ASSOCIATED_PROJECT_IDS = "SELECT project_id FROM project_auditor_mapping " +
			"WHERE audit_complete = 0 " +
			"AND auditor_id= :auditorId;"; // AND ( audit_date >= :audit_start_date AND audit_date < :audit_end_date); ";

	private static final String GET_PROJECT_AUDITOR_LIST = "SELECT id, project_id, auditor_id, audit_date, audit_complete, " +
			"created_by, created_on, last_modified_by, last_modified_on FROM project_auditor_mapping order by audit_date desc";

	private static final String GET_PROJECT_AUDITOR_BY_ID = "SELECT id, project_id, auditor_id, audit_date, audit_complete, created_by, " +
			" created_on, last_modified_by, last_modified_on FROM project_auditor_mapping " +
			" WHERE project_id = :project_id AND " +
			" last_modified_on = (SELECT MAX(last_modified_on) FROM project_auditor_mapping " +
			" WHERE project_id = :proj_id HAVING MAX(last_modified_on)); ";
	
	private static final String UPDATE_PROJECT_AUDITOR_BY_ID = "UPDATE project_auditor_mapping SET auditor_id = :auditor_id, " +
	"audit_date = :audit_date, last_modified_by = :last_modified_by, last_modified_on = :last_modified_on WHERE id = :id";

	private static final String INSERT_PROJECT_AUDITOR = "INSERT INTO project_auditor_mapping(project_id, auditor_id, audit_date, " +
			"audit_complete, created_by, created_on, last_modified_by, last_modified_on) VALUES(?,?,?,?,?,?,?,?)";

	private static final String INSERT_AUDITOR = "INSERT INTO auditor " +
			" ( ace_no, name, active, dept_id, dept_name, email, created_by, created_on, last_modified_by, last_modified_on ) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW());";
	
	private static final String GET_LAST_AUDIT_COMPLETEDDATE_PROJECT = "SELECT audit_date FROM project_auditor_mapping " +
			"WHERE project_id = :projectid AND audit_date = (" +
			"SELECT MAX(audit_date) FROM project_auditor_mapping WHERE project_id = :id AND audit_complete = 1 HAVING MAX(audit_date));";

	@Override
	public List<ProjectAuditor> getProjectAuditorList() {
		LOGGER.debug("Getting ALL ProjectAuditorMapping!");
		List<ProjectAuditor> projectAuditors = getSimpleJdbcTemplate().query(
				GET_PROJECT_AUDITOR_LIST, new ProjectAuditorMapper());
		return projectAuditors;
	}

	@Override
	public ProjectAuditor getProjectAuditorByID(int id) throws SQLException{
		ProjectAuditor projectAuditor =null;
		try{
			projectAuditor = getSimpleJdbcTemplate().queryForObject(
					GET_PROJECT_AUDITOR_BY_ID, new ProjectAuditorMapper(),
					new MapSqlParameterSource().addValue("project_id", id)
					.addValue("proj_id", id));
		}
		catch(EmptyResultDataAccessException e){
		}
		return projectAuditor;
	}
	
	@Override
	public boolean createAuditor(Auditor auditor) {
		boolean isCreated = true;
		try {
			if(auditor.getAceNo() != null && auditor.getName() != null
					&& auditor.getDeptID() != null && auditor.getDeptName() != null 
					&& auditor.getEmail() != null && auditor.getCreatedBy() != null
					&& auditor.getLastModifiedBy() != null) {
				//check if the auditor exists in the system
				StringBuffer sb = new StringBuffer(3200);
				sb.append("select count(id) from auditor");
				sb.append(" where ace_no = '"+auditor.getAceNo()+"'");
				int auditorCount = getSimpleJdbcTemplate().queryForInt(sb.toString());
				if(auditorCount>0){
					StringBuffer activateAuditorSB = new StringBuffer(3200);
					activateAuditorSB.append("update auditor");
					activateAuditorSB.append(" set active = 1");
					activateAuditorSB.append(" where ace_no = '"+auditor.getAceNo()+"'");
					getSimpleJdbcTemplate().update(activateAuditorSB.toString());
				}else{
				getSimpleJdbcTemplate().update(
						INSERT_AUDITOR, new Object[]{
								auditor.getAceNo(), auditor.getName(),
								auditor.isActive(), auditor.getDeptID(),
							auditor.getDeptName(), auditor.getEmail(),
								auditor.getCreatedBy(), auditor.getLastModifiedBy()});
				}
			}
		} catch(Exception exp) {
			LOGGER.error("Error occured while saving auditor details.." + exp.getMessage());
			isCreated = false;
		}
		return isCreated;
	}
	
	@Override
	public String removeAuditor(String aceNo,String lastModifiedBy){
		StringBuffer sb = new StringBuffer(3200);
		sb.append("update auditor");
		sb.append(" set active = 0");
		sb.append(" ,last_modified_by = '"+lastModifiedBy+"' ,last_modified_on = now()");
		sb.append(" where ace_no = '"+aceNo+"'");
		
		
		StringBuffer sb1 = new StringBuffer(3200);
		sb1.append("select count(auditor_id) from project_auditor_mapping");
		sb1.append(" where audit_complete = 0 and auditor_id in(select id from auditor");
		sb1.append(" where ace_no='"+aceNo+"')");
		if(aceNo == null){
			return "Unable to remove auditor - ACENO is missing";
		}else if(aceNo.trim().equals("")){
			return "Unable to remove auditor - ACENO is missing";
		}
		if(lastModifiedBy == null){
			return "Unable to remove auditor";
		}else if(lastModifiedBy.trim().equals("")){
			return "Unable to remove auditor";
		}
		try{
			//check for projects assigned to the auditor
			int project_audit_count = getSimpleJdbcTemplate().queryForInt(sb1.toString());
			if(project_audit_count>0){
				return "Unable to remove auditor - Project audit pending";
			}
			//set active = 0 for that auditor
			getSimpleJdbcTemplate().update(sb.toString());
			System.out.println("disable auditor: "+sb.toString());
			return "Auditor removed successfully";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "Unable to remove auditor";
		
	}

	@Override
	public List<ProjectAuditorHelper> getProjectAuditorsByProjectId(String projectId) {
		
		List<ProjectAuditorHelper> projectAuditorHelperList = new ArrayList<ProjectAuditorHelper>();

		StringBuilder sql = new StringBuilder("SELECT pa.id as id, pa.project_id as project_id, pa.auditor_id as auditor_id, " +
				" pa.audit_date as audit_date, pa.audit_complete as audit_complete, pa.created_by as created_by, " +
				" pa.created_on as created_on, pa.last_modified_by as last_modified_by, pa.last_modified_on as last_modified_on, " +
				" a.name as name, p.prj_name as project_name FROM project_auditor_mapping pa, auditor a, project p WHERE " +
				" pa.auditor_id = a.id AND pa.project_id = p.id");

		sql.append(" AND pa.project_id = ");
		sql.append(projectId);
		sql.append( " ORDER BY audit_date DESC ");
		
		projectAuditorHelperList = getSimpleJdbcTemplate().query(
				sql.toString(), new ProjAuditorMapper());
		return projectAuditorHelperList;		
	}

	
	@Override
	public List<ProjectAuditorHelper> searchProjectAuditors(String thiName, Date startDate, Date endDate, boolean auditComlpete) {

		StringBuilder sql = new StringBuilder("SELECT pa.id as id, pa.project_id as project_id, pa.auditor_id as auditor_id, pa.audit_date as audit_date, pa.audit_complete as audit_complete, pa.created_by as created_by, pa.created_on as created_on, pa.last_modified_by as last_modified_by, pa.last_modified_on as last_modified_on, a.name as name, p.prj_name as project_name FROM project_auditor_mapping pa, auditor a, project p WHERE");

		if(startDate != null) {
			sql.append(" pa.audit_date >= '");
			sql.append(startDate);
			sql.append("'");
			if(endDate != null || (thiName != null  && thiName.trim().length() > 0)) {
				sql.append(" AND ");
			}
		}
		if(endDate != null) {
			sql.append(" pa.audit_date <= '");
			sql.append(endDate);
			sql.append("'");
			if(thiName != null  && thiName.trim().length() > 0) {
				sql.append(" AND ");
			}
		}
		if(thiName != null && thiName.trim().length() > 0) {
			sql.append(" pa.project_id IN (SELECT id FROM project proj WHERE prj_name LIKE '%");
			sql.append(thiName);
			sql.append("%' AND pa.project_id = proj.id)");
		}
		if(auditComlpete) {
			sql.append(" AND pa.audit_complete = TRUE");
		} else {
			sql.append(" AND pa.audit_complete = FALSE");
		}
		sql.append(" AND pa.auditor_id = a.id AND pa.project_id = p.id");
		
		sql.append( " ORDER BY audit_date DESC ");
		
		List<ProjectAuditorHelper> projectAuditors = getSimpleJdbcTemplate().query(
				sql.toString(), new ProjAuditorMapper());
		return projectAuditors;		
	}

	@Override
	public boolean saveProjectAuditor(ProjectAuditor projAuditor) throws SQLException{

		int count = 0;
		int count1 = 0;	//for project_auditor_history row update
		boolean isRowUpdated = false;
		
		Date lastAuditComplete = null;
		
		try {
			lastAuditComplete = getSimpleJdbcTemplate().queryForObject(
					GET_LAST_AUDIT_COMPLETEDDATE_PROJECT, Date.class, 
					new MapSqlParameterSource().addValue("projectid", projAuditor.getProjectId())
					.addValue("id", projAuditor.getProjectId()));
			
		} catch (EmptyResultDataAccessException e) {
			
		}
		
		
		try{
			LOGGER.debug("Saving ProjectAuditor");
			
			//int lastAuditor = getProjectAuditorId(projAuditor.getProjectId());
			int auditFrequency = getSimpleJdbcTemplate().queryForInt(
									"SELECT audit_freq FROM project WHERE id = :id",
									new MapSqlParameterSource().addValue("id", projAuditor.getProjectId()));

			if (isValidAuditDate(projAuditor, lastAuditComplete, auditFrequency)) {
				int inCompleteProjectAuditId = getInCompleteProjectAuditRecord(projAuditor);
				if (inCompleteProjectAuditId > 0) {
					count = getSimpleJdbcTemplate().update(
							UPDATE_PROJECT_AUDITOR_BY_ID, 
								new MapSqlParameterSource()
									.addValue("auditor_id", projAuditor.getAuditorId())
									.addValue("audit_date", projAuditor.getAuditDate())
									.addValue("last_modified_by", projAuditor.getLastModifiedBy())
									.addValue("last_modified_on", projAuditor.getLastModifiedOn())
									.addValue("id", inCompleteProjectAuditId));
				} else {
					count = getSimpleJdbcTemplate().update(
							INSERT_PROJECT_AUDITOR, new Object[]{
									projAuditor.getProjectId(), projAuditor.getAuditorId(),
									projAuditor.getAuditDate(), projAuditor.getAuditComplete(),
									projAuditor.getCreatedBy(), projAuditor.getCreatedOn(),
									projAuditor.getLastModifiedBy(), projAuditor.getLastModifiedOn()});
				}
				if(count > 0){
					//update the project_auditor_history
					StringBuffer sb = new StringBuffer(3200);
					sb.append("insert into project_auditor_history(project_id,auditor_id");
					sb.append(" ,created_on,created_by) values(?,?,now(),?)");
					count1 = getSimpleJdbcTemplate().update(
							sb.toString(), new Object[]{
									projAuditor.getProjectId(), projAuditor.getAuditorId(),
									projAuditor.getCreatedBy()});
				}
				isRowUpdated = true;
			}
			
/*			String startDateStr = computeStartDate(projAuditor.getAuditDate());
			String endDateStr = computeEndDate(projAuditor.getAuditDate());
			projectAuditor = getProjectAuditorByID(projAuditor.getProjectId());
			if(projectAuditor!=null){
				projectAuditorId = projectAuditor.getId();
			}
			if(projectAuditorId !=null && projectAuditorId > 0 && !projectAuditor.getAuditComplete()) {			
				count = getSimpleJdbcTemplate().update(
						UPDATE_PROJECT_AUDITOR_BY_ID, 
							new MapSqlParameterSource()
								.addValue("auditor_id", projAuditor.getAuditorId())
								.addValue("audit_date", projAuditor.getAuditDate())
								.addValue("last_modified_by", projAuditor.getLastModifiedBy())
								.addValue("last_modified_on", projAuditor.getLastModifiedOn())
								.addValue("id", projectAuditorId));
				isRowUpdated = true;
			} else {
				List<ProjectAuditor> projectAuditors = getSimpleJdbcTemplate().query(
						"SELECT id, project_id, auditor_id, audit_date, audit_complete, created_by, created_on, " +
						" last_modified_by, last_modified_on FROM project_auditor_mapping" +
						" WHERE project_id = ? AND (audit_date >= ? AND audit_date < ?)", 
						new ProjectAuditorMapper() , 
						new Object[]{ projAuditor.getProjectId(), Date.valueOf(startDateStr), Date.valueOf(endDateStr)});
				if(projectAuditors == null || (projectAuditors != null && projectAuditors.size() < 1)) {
					count = getSimpleJdbcTemplate().update(
							INSERT_PROJECT_AUDITOR, new Object[]{
									projAuditor.getProjectId(), projAuditor.getAuditorId(),
									projAuditor.getAuditDate(), projAuditor.getAuditComplete(),
									projAuditor.getCreatedBy(), projAuditor.getCreatedOn(),
									projAuditor.getLastModifiedBy(), projAuditor.getLastModifiedOn()});
					isRowUpdated = true;
				}
			}
*/			LOGGER.debug("Rows updated: " + count);
			LOGGER.debug("Project-Auditor history updated, count :"+count1);
			}catch(EmptyResultDataAccessException e){
				
			}
		return isRowUpdated;
	}

	@Override
	public boolean isAuditor(String aceNo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("aceNo", aceNo);
		int count = getSimpleJdbcTemplate().queryForInt(IS_AUDITOR, params);
		return (count > 0);
	}

	@Override
	public int getAuditorId(String aceNo) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("aceNo", aceNo);
		int id = getSimpleJdbcTemplate().queryForInt(GET_AUDITOR_ID,params);
		return id;
	}
	
	@Override
	public int getProjectAuditorId(int projectId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("project_id",projectId);
		StringBuffer sb = new StringBuffer(3200);
		sb.append("select auditor_id from project_auditor_mapping");
		sb.append(" where project_id=:project_id");
		sb.append(" order by last_modified_on desc limit 1");
		int id = getSimpleJdbcTemplate().queryForInt(sb.toString(),params);
		return id;
	}

	@Override
	public List<Integer> getAssociatedProjects(int auditorId) {
		/*
		java.util.Date ctDate = Calendar.getInstance().getTime();
		ctDate = DateUtils.setDays(ctDate, 1);
		java.util.Date startsOn = ctDate;
		int ctMonth = ctDate.getMonth() + 1;
		if(ctMonth % 2 == 0){
			startsOn = DateUtils.addMonths(ctDate, -1);
		}
		java.util.Date endsOn = DateUtils.addMonths(startsOn, 2);
		*/
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("auditorId", auditorId);
/*		params.addValue("audit_start_date", new Date(startsOn.getTime()));
		params.addValue("audit_end_date", new Date(endsOn.getTime()));
*/		List<Integer> prjIds = getSimpleJdbcTemplate().query(GET_ASSOCIATED_PROJECT_IDS, new AssProjectIdMapper(), params);
		List<Integer> validProjectIds = new ArrayList<Integer>();
		for(Integer id: prjIds){
			if(isValidProjectId(id)){
				validProjectIds.add(id);
			}
		}
		return validProjectIds;
	}
	
	private boolean isValidProjectId(Integer projectId){
		int auditFreq = getSimpleJdbcTemplate().queryForInt("select audit_freq from project where id="+projectId);
		Calendar cal  = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//System.out.println(auditCycleDate.toString());
		java.util.Date startsOn = cal.getTime();
		java.util.Date endsOn = null;
		if(auditFreq == 1){
				cal.add(Calendar.MONTH, 1);
				endsOn = cal.getTime();
		}else if(auditFreq == 2){
				int ctMonth = cal.get(Calendar.MONTH)+1;
			if(ctMonth % 2 == 0){
				cal.add(Calendar.MONTH, -1);
				startsOn = cal.getTime();
			}
			cal.add(Calendar.MONTH, 2);
			endsOn = cal.getTime(); 
		}
		StringBuffer sb = new StringBuffer(3200);
		sb.append("select count(project_id) from project_auditor_mapping where audit_complete=0");
		sb.append(" and project_id = :projectId and (audit_date >= :startson and audit_date < :endson)");
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("projectId", projectId);
		map.addValue("startson",startsOn);
		map.addValue("endson", endsOn);
		int projCount = getSimpleJdbcTemplate().queryForInt(sb.toString(), map);
		if(projCount>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<Auditor> getAuditorList() {
		LOGGER.debug("Getting ALL Auditor!");
		List<Auditor> auditors = getSimpleJdbcTemplate().query(
				GET_AUDITOR_LIST, new AuditorMapper());
		return auditors;
	}

	@Override
	public Auditor getAuditorByID(int id) {
		Auditor auditor = getSimpleJdbcTemplate().queryForObject(
				GET_AUDITOR_BY_ID, new AuditorMapper(),
				new MapSqlParameterSource().addValue("id", id));
		return auditor;
	}


	private static class AuditorMapper implements ParameterizedRowMapper<Auditor> {

		@Override
		public Auditor mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Auditor auditor = new Auditor();
			
			auditor.setId(rs.getInt("id"));
			auditor.setAceNo(rs.getString("ace_no"));
			auditor.setName(rs.getString("name"));
			auditor.setActive(rs.getBoolean("active"));
			auditor.setDeptID(rs.getString("dept_id"));
			auditor.setDeptName(rs.getString("dept_name"));
			auditor.setEmail(rs.getString("email"));
			auditor.setCreatedBy(rs.getString("created_by"));
			auditor.setCreatedOn(rs.getDate("created_on"));
			auditor.setLastModifiedBy(rs.getString("last_modified_by"));
			auditor.setLastModifiedOn(rs.getDate("last_modified_on"));
			
			return auditor;
		}
		
	}
	
	private static class AssProjectIdMapper implements ParameterizedRowMapper<Integer> {

		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			int prjId = rs.getInt(1);
			return prjId;
		}

	}

	private static class ProjectAuditorMapper implements ParameterizedRowMapper<ProjectAuditor> {

		@Override
		public ProjectAuditor mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ProjectAuditor projectAuditor = new ProjectAuditor();
			
			projectAuditor.setId(rs.getInt("id"));
			projectAuditor.setAuditComplete(rs.getBoolean("audit_complete"));
			projectAuditor.setAuditorId(rs.getInt("auditor_id"));
			projectAuditor.setProjectId(rs.getInt("project_id"));
			projectAuditor.setAuditDate(rs.getDate("audit_date"));
			projectAuditor.setCreatedBy(rs.getString("created_by"));
			projectAuditor.setCreatedOn(rs.getDate("created_on"));
			projectAuditor.setLastModifiedBy(rs.getString("last_modified_by"));
			projectAuditor.setLastModifiedOn(rs.getDate("last_modified_on"));
			
			return projectAuditor;
		}
		
	}

	private static class ProjAuditorMapper implements ParameterizedRowMapper<ProjectAuditorHelper> {

		@Override
		public ProjectAuditorHelper mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ProjectAuditorHelper projectAuditorHelper = new ProjectAuditorHelper();
			
			projectAuditorHelper.setId(rs.getInt("id"));
			projectAuditorHelper.setAuditComplete(rs.getBoolean("audit_complete"));
			projectAuditorHelper.setAuditorId(rs.getInt("auditor_id"));
			projectAuditorHelper.setProjectId(rs.getInt("project_id"));
			projectAuditorHelper.setAuditDate(rs.getDate("audit_date"));
			projectAuditorHelper.setAuditorName(rs.getString("name"));
			projectAuditorHelper.setProjectName(rs.getString("project_name"));
			
			return projectAuditorHelper;
		}
		
	}

	private boolean isValidAuditDate(ProjectAuditor projectAuditor, Date lastAuditComplete, int auditFrequency) {
		
		LocalDate lastAuditCompletedDate = null;
		LocalDate auditDate = new LocalDate(projectAuditor.getAuditDate().getTime());
		
		if (lastAuditComplete != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(lastAuditComplete);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			lastAuditCompletedDate = new LocalDate(cal.getTime());
		}
		
		
		if (lastAuditCompletedDate != null) {
			LocalDate endDate = lastAuditCompletedDate.plusMonths(auditFrequency);
			
			if (!(auditDate.equals(endDate) || auditDate.isAfter(endDate))) {
				return false;
			}/*else {
				List<ProjectAuditor> projectAuditors = getSimpleJdbcTemplate().query(
										"SELECT id, project_id, auditor_id, audit_date, audit_complete, created_by, created_on, " +
										" last_modified_by, last_modified_on FROM project_auditor_mapping" +
										" WHERE project_id = ? AND (audit_date >= ? AND audit_date =< ?) AND audit_complete = 1", 
										new ProjectAuditorMapper() , 
										new Object[]{ projectAuditor.getProjectId(), 
											Date.valueOf(startDate.toString()), 
											Date.valueOf(endDate.toString())});
				
				if (projectAuditors != null && !projectAuditors.isEmpty()) {
					return false;
				}
			}*/
		}
		return true;
	}
	
	private int getInCompleteProjectAuditRecord (ProjectAuditor projectAuditor) {

		int projectAuditorID = 0;
		
		try {
			projectAuditorID = getSimpleJdbcTemplate().queryForInt(
					"SELECT id FROM project_auditor_mapping" +
					" WHERE project_id = ? AND audit_complete = 0", 
					new Object[]{ projectAuditor.getProjectId()});
		} catch (EmptyResultDataAccessException e) {
			
		}
		
		return projectAuditorID;
		
	}
/*	private String computeStartDate(LocalDate lastCompletedAuditDate) {
		lastCompletedAuditDate.plusDays(1);
		return lastCompletedAuditDate.toString();
	}
	
	private String computeEndDate(LocalDate lastCompletedAuditDate, int auditFrequency) {
		lastCompletedAuditDate.plusMonths(auditFrequency);
		return lastCompletedAuditDate.toString();
	}
*/}
