package com.aspire.thi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.CollectionUtils;

import com.aspire.thi.domain.AssesmentGroupScore;
import com.aspire.thi.domain.AssesmentType;
import com.aspire.thi.domain.LineItemLog;
import com.aspire.thi.domain.LineItemWeight;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.Weitage;
import com.aspire.thi.domain.Auditor;

public class JdbcThiScoreDao extends SimpleJdbcDaoSupport implements ThiScoreRepository {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	private Properties scoreproperties;

	private static class ThiScoreMapper implements ParameterizedRowMapper<ThiScore> {
		@Override
		public ThiScore mapRow(ResultSet rs, int rowNum) throws SQLException {
			ThiScore score = new ThiScore();
			score.setId(rs.getInt("id"));
			score.setProjectId(rs.getInt("project_id"));
			score.setAuditorId(rs.getInt("auditor_id"));
			score.setAuditDate(rs.getDate("audit_date"));
			score.setAuditCycleDate(rs.getDate("audit_cycle_date"));
			score.setOverallScore(rs.getDouble("overall_score"));
			score.setComments(rs.getString("comments"));
			score.setRecommendations(rs.getString("recommendations"));
			score.setProjectOwnerId(rs.getString("project_owner_id"));
			score.setAuditorName(rs.getString("name"));
			score.setProjectName(rs.getString("prj_name"));
			score.setAssesmentType(rs.getInt("assesment_type_id"));
			return score;
		}

	}

	private static class AssesmentGroupScoreMapper implements ParameterizedRowMapper<AssesmentGroupScore> {
		@Override
		public AssesmentGroupScore mapRow(ResultSet rs, int rowNum) throws SQLException {
			AssesmentGroupScore score = new AssesmentGroupScore();
			score.setId(rs.getInt("id"));
			score.setAssesmentGroupId(rs.getInt("assesment_group_id"));
			score.setDescription(rs.getString("description"));
			score.setName(rs.getString("group_name"));
			score.setScore(rs.getDouble("score"));
			return score;
		}
	}

	private static class AssesmentGroupMapper implements ParameterizedRowMapper<AssesmentGroupScore> {
		@Override
		public AssesmentGroupScore mapRow(ResultSet rs, int rowNum) throws SQLException {
			AssesmentGroupScore score = new AssesmentGroupScore();
			score.setAssesmentGroupId(rs.getInt("id"));
			score.setDescription(rs.getString("description"));
			score.setName(rs.getString("group_name"));
			return score;
		}
	}

	private static class LineItemMapper implements ParameterizedRowMapper<LineItemLog> {
		@Override
		public LineItemLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			LineItemLog lineItem = new LineItemLog();
			lineItem.setLineItemId(rs.getInt("id"));
			lineItem.setDescription(rs.getString("line_item_description"));
			lineItem.setText(rs.getString("line_item_text"));
			return lineItem; // one1
		}
	}

	private static class LineItemLogMapper implements ParameterizedRowMapper<LineItemLog> {
		@Override
		public LineItemLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			LineItemLog lineItem = new LineItemLog();
			lineItem.setId(rs.getInt("id"));
			lineItem.setDescription(rs.getString("line_item_description"));
			lineItem.setText(rs.getString("line_item_text"));
			lineItem.setComments(rs.getString("comments"));
			lineItem.setLineItemId(rs.getInt("line_item_id"));
			lineItem.setScore(rs.getInt("score"));
			return lineItem; // two2
		}
	}


	private static class ProjectAuditorMapper implements ParameterizedRowMapper<ProjectAuditor> {
		@Override
		public ProjectAuditor mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectAuditor auditMap = new ProjectAuditor();
			auditMap.setId(rs.getInt("id"));
			auditMap.setProjectId(rs.getInt("project_id"));
			auditMap.setAuditorId(rs.getInt("auditor_id"));
			auditMap.setAuditDate(rs.getDate("audit_date"));
			auditMap.setAuditComplete(rs.getBoolean("audit_complete"));
			return auditMap;
		}

	}

	private static class ProjectMapper implements ParameterizedRowMapper<Project> {
		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project auditMap = new Project();
			auditMap.setId(rs.getInt("id"));
			auditMap.setAuditFrequency(rs.getInt("audit_freq"));
			return auditMap;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public List<AssesmentGroupScore> getAssesmentGroupScores(Integer thiScoreId) {
		
		
		String assessmentName= getAssesmentName(getAssesmentIdByThiId(thiScoreId));
		List<AssesmentGroupScore> assesmentGroupScores = getSimpleJdbcTemplate().query(
				"SELECT gscore.id AS id, agroup.id AS assesment_group_id, score, group_name, description FROM thi_group_score gscore INNER JOIN assesment_group agroup ON gscore.assessment_group_id = agroup.id WHERE thi_score_id = ?",
				new AssesmentGroupScoreMapper(), thiScoreId);
		
		//List<AssesmentGroupScore> assesmentGroupScoresUpdated=null;
		
		for (AssesmentGroupScore groupScore : assesmentGroupScores) {
			// score and logs added with group score vkp
			groupScore.setLineItemLogs(this.getLineItemLogs(groupScore.getId()));
			List<LineItemLog> updatedlineItemsList = new ArrayList<LineItemLog>();
			for (LineItemLog lineItemLog : groupScore.getLineItemLogs()) {
				String key = assessmentName+"."+groupScore.getName()+"."+lineItemLog.getText();
				key=key.replaceAll(" ", "").toLowerCase();
			//	System.out.println("key= "+key +" Value= "+scoreproperties.getProperty(key));
				lineItemLog.setPercentage(scoreproperties.getProperty(key));
				updatedlineItemsList.add(lineItemLog);
			}
			groupScore.setLineItemLogs(updatedlineItemsList);			
			
		}
		
		return assesmentGroupScores;
		
	}

	//read line item log and score while starting ..vkp
	@Override
	public List<LineItemLog> getLineItemLogs(Integer assesmentGroupScoreId) { 
		@SuppressWarnings("deprecation")
		List<LineItemLog> itemLogs = getSimpleJdbcTemplate().query(
				"SELECT itemLog.id as id,lineItem.id AS line_item_id, comments, line_item_description, line_item_text, itemLog.score FROM thi_line_item_log itemLog INNER JOIN assesment_line_item lineItem ON itemLog.assesment_line_item_id = lineItem.id WHERE thi_group_score_id = ?",
				new LineItemLogMapper(), assesmentGroupScoreId);

		return itemLogs; // called two
	}

	public List<String> getAuditeeByProjectScoreId(int scoreId) {
		StringBuffer sbSelectAuditee = new StringBuffer(3200);
		sbSelectAuditee.append("select auditee_name from project_auditee_mapping");
		sbSelectAuditee.append(" where score_id=" + scoreId);
		List<String> auditeeName = getSimpleJdbcTemplate().query(sbSelectAuditee.toString(), new AuditeeNameMapper());

		return auditeeName;

	}

	@Override
	@SuppressWarnings("deprecation")
	public ThiScore getThiScore(Integer projectId, Date auditCycleDate) {

		/*
		 * auditCycleDate = DateUtils.setDays(auditCycleDate, 1); Date startsOn
		 * = auditCycleDate; int ctMonth = auditCycleDate.getMonth() + 1; if
		 * (ctMonth % 2 == 0) { startsOn = DateUtils.addMonths(auditCycleDate,
		 * -1); } Date endsOn = DateUtils.addMonths(startsOn, 2);
		 */
		// find the audit frequency to set the from date and to date
		StringBuffer sbAuditFreq = new StringBuffer(3200);
		sbAuditFreq.append("SELECT id, audit_freq");
		sbAuditFreq.append(" FROM project");
		sbAuditFreq.append(" WHERE id = ?");
		Project proj = null;
		try {
			proj = getSimpleJdbcTemplate().queryForObject(sbAuditFreq.toString(), new ProjectMapper(),
					new Object[] { projectId });
		} catch (EmptyResultDataAccessException e) {

		}
		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		//System.out.println(auditCycleDate.toString());
		Date startsOn = auditCycleDate;
		Date endsOn = null;
		if (proj != null) {
			if (proj.getAuditFrequency() == 1) {
				endsOn = DateUtils.addMonths(startsOn, 1);
			} else if (proj.getAuditFrequency() == 2) {
				int ctMonth = auditCycleDate.getMonth() + 1;
				if (ctMonth % 2 == 0) {
					startsOn = DateUtils.addMonths(auditCycleDate, -1);
				}
				endsOn = DateUtils.addMonths(startsOn, 2);
			}
		}
		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date completionDate = new java.sql.Date(endsOn.getTime());
		logger.info("Completion day - date :: " + completionDate);

		ThiScore thiScore = null;
		StringBuffer sb = new StringBuffer(3200);
		sb.append("SELECT score.id, score.project_id, prj_name, score.auditor_id, score.audit_date,");
		sb.append("audit_cycle_date, overall_score, assesment_type_id,");
		sb.append(" comments, recommendations, project_owner_id, auditor.name FROM thi_score score");
		sb.append(" INNER JOIN auditor auditor ON score.auditor_id = auditor.id");
		sb.append(" INNER JOIN project proj ON score.project_id = proj.id");
		// sb.append(" inner join project_auditor_mapping pam on
		// pam.project_id=proj.id");
		// sb.append(" and pam.audit_date=score.audit_date");
		sb.append(" WHERE score.project_id = ? AND ( audit_cycle_date >= ? AND audit_cycle_date < ?)");
		// sb.append(" and pam.audit_complete=0 order by audit_cycle_date desc
		// limit 1");
		try {
			thiScore = getSimpleJdbcTemplate().queryForObject(
					/*
					 * "SELECT score.id, project_id, prj_name, auditor_id, audit_date, audit_cycle_date, overall_score, comments, recommendations, project_owner_id, auditor.name FROM thi_score score INNER JOIN auditor auditor ON score.auditor_id = auditor.id INNER JOIN project proj ON score.project_id = proj.id WHERE project_id = ? AND ( audit_cycle_date >= ? AND audit_cycle_date < ?) order by audit_date desc limit 1"
					 * ,
					 */
					sb.toString(), new ThiScoreMapper(), new Object[] { projectId, beginDate, completionDate });
			thiScore.setAssesmentGroupScores(this.getAssesmentGroupScores(thiScore.getId()));
			if (CollectionUtils.isEmpty(thiScore.getAssesmentGroupScores()) == Boolean.FALSE) {
				for (AssesmentGroupScore groupScore : thiScore.getAssesmentGroupScores()) {
					
					// score and logs added with group score vkp
						groupScore.setLineItemLogs(this.getLineItemLogs(groupScore.getId()));
						List<LineItemLog> updatedlineItemsList = new ArrayList<LineItemLog>();
						for (LineItemLog lineItemLog : groupScore.getLineItemLogs()) {
							String key = getAssesmentName(thiScore.getAssesmentType())+"."+groupScore.getName()+"."+lineItemLog.getText();
							key=key.replaceAll(" ", "").toLowerCase();
							//System.out.println("key= "+key +" Value= "+scoreproperties.getProperty(key));
							lineItemLog.setPercentage(scoreproperties.getProperty(key));
							updatedlineItemsList.add(lineItemLog);
						}
						groupScore.setLineItemLogs(updatedlineItemsList);			
				}
			}
		} catch (EmptyResultDataAccessException e) {

		}
		if (thiScore != null) {
			List<String> auditeeList = getAuditeeByProjectScoreId(thiScore.getId());
			thiScore.setAuditeeNames(auditeeList);
		}
		return thiScore;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ThiScore getThiAudit(Integer projectId, Date auditCycleDate, Integer auditorId, Integer assignmentTypeId) {

		/*
		 * auditCycleDate = DateUtils.setDays(auditCycleDate, 1); Date startsOn
		 * = auditCycleDate; int ctMonth = auditCycleDate.getMonth() + 1; if
		 * (ctMonth % 2 == 0) { startsOn = DateUtils.addMonths(auditCycleDate,
		 * -1); } Date endsOn = DateUtils.addMonths(startsOn, 2);
		 */
		// find the audit frequency to set the from date and to date
		StringBuffer sbAuditFreq = new StringBuffer(3200);
		sbAuditFreq.append("SELECT id, audit_freq");
		sbAuditFreq.append(" FROM project");
		sbAuditFreq.append(" WHERE id = ?");
		Project proj = null;
		try {
			proj = getSimpleJdbcTemplate().queryForObject(sbAuditFreq.toString(), new ProjectMapper(),
					new Object[] { projectId });
		} catch (EmptyResultDataAccessException e) {

		}
		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		//System.out.println(auditCycleDate.toString());
		Date startsOn = auditCycleDate;
		Date endsOn = null;
		if (proj != null) {
			if (proj.getAuditFrequency() == 1) {
				endsOn = DateUtils.addMonths(startsOn, 1);
			} else if (proj.getAuditFrequency() == 2) {
				int ctMonth = auditCycleDate.getMonth() + 1;
				if (ctMonth % 2 == 0) {
					startsOn = DateUtils.addMonths(auditCycleDate, -1);
				}
				endsOn = DateUtils.addMonths(startsOn, 2);
			}
		}
		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date completionDate = new java.sql.Date(endsOn.getTime());
		logger.info("Completion day - date :: " + completionDate);

		ThiScore thiScore = null;
		StringBuffer sb = new StringBuffer(3200);
		sb.append("SELECT id, project_id, auditor_id, score.audit_date, audit_cycle_date, overall_score,");
		sb.append(" comments, recommendations, project_owner_id");
		sb.append(" FROM thi_score");
		// sb.append(" inner join project_auditor_mapping pam on pam.project_id
		// = score.project_id");
		// sb.append(" and pam.audit_date = score.audit_date");
		sb.append(" WHERE project_id = ? AND ( audit_date >= '?' AND audit_date < '?')");
		// sb.append(" pam.audit_complete=0 order by score.audit_date desc limit
		// 1");
		try {
			thiScore = getSimpleJdbcTemplate().queryForObject(
					/*
					 * "SELECT id, project_id, auditor_id, audit_date, audit_cycle_date, overall_score, comments, recommendations, project_owner_id FROM thi_score WHERE project_id = ? AND ( audit_date >= ? AND audit_date < ?) order by audit_date desc limit 1"
					 * ,
					 */
					sb.toString(), new ThiScoreMapper(), new Object[] { projectId, beginDate, completionDate });
			thiScore.setAssesmentGroupScores(this.getAssesmentGroupScores(thiScore.getId()));
			if (CollectionUtils.isEmpty(thiScore.getAssesmentGroupScores()) == Boolean.FALSE) {
				String assessmentName = getAssesmentName(assignmentTypeId);
				for (AssesmentGroupScore groupScore : thiScore.getAssesmentGroupScores()) {
					groupScore.setLineItemLogs(this.getLineItemLogs(groupScore.getId()));
					
					groupScore.setLineItemLogs(this.getLineItemLogs(groupScore.getId()));
					List<LineItemLog> updatedlineItemsList = new ArrayList<LineItemLog>();
					for (LineItemLog lineItemLog : groupScore.getLineItemLogs()) {
						String key = assessmentName+"."+groupScore.getName()+"."+lineItemLog.getText();
						key=key.replaceAll(" ", "").toLowerCase();
//						System.out.println("key= "+key +" Value= "+scoreproperties.getProperty(key));
						lineItemLog.setPercentage(scoreproperties.getProperty(key));
						updatedlineItemsList.add(lineItemLog);
					}
					groupScore.setLineItemLogs(updatedlineItemsList);
					
					
				}
			}
		} catch (EmptyResultDataAccessException e) {

		}
		return thiScore;
	}

	@Override
	public List<AssesmentGroupScore> getAssesmentGroups(Integer assignmentTypeId) {
		@SuppressWarnings("deprecation")
		List<AssesmentGroupScore> assesmentGroupScores = getSimpleJdbcTemplate().query(
				"SELECT id, group_name, description FROM assesment_group WHERE assesment_type_id = ?",
				new AssesmentGroupMapper(), assignmentTypeId);
		return assesmentGroupScores;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<LineItemLog> getAssesmentLineItems(Integer assignmentType, Integer assesmentGroupId) { // one1
		List<LineItemLog> itemLogs = getSimpleJdbcTemplate().query(
				"SELECT id, line_item_text, line_item_description FROM assesment_line_item WHERE assesment_type_id = ? AND assesment_group_id = ?",
				new LineItemMapper(), assignmentType, assesmentGroupId);
		return itemLogs; // called one
	}

	@Override
	public ThiScore getAuditScore(Integer assignmentType) {
		ThiScore auditData = new ThiScore();
		String assessmentName = getAssesmentName(assignmentType);
		auditData.setAssesmentType(assignmentType);
		auditData.setAssesmentGroupScores(getAssesmentGroups(assignmentType));
		if (CollectionUtils.isEmpty(auditData.getAssesmentGroupScores()) == Boolean.FALSE) {
			for (AssesmentGroupScore groupScore : auditData.getAssesmentGroupScores()) {
				// score and logs added with group score vkp
				List<LineItemLog> lineItemsList =this.getAssesmentLineItems(assignmentType, groupScore.getAssesmentGroupId());
				List<LineItemLog> updatedlineItemsList = new ArrayList<LineItemLog>();
				for (Iterator iterator = lineItemsList.iterator(); iterator.hasNext();) {
					LineItemLog lineItemLog = (LineItemLog) iterator.next();
					String key = assessmentName+"."+groupScore.getName()+"."+lineItemLog.getText();
					key=key.replaceAll(" ", "").toLowerCase();
//					System.out.println("key= "+key +" Value= "+scoreproperties.getProperty(key));
					lineItemLog.setPercentage(scoreproperties.getProperty(key));
					updatedlineItemsList.add(lineItemLog);
				}
				groupScore.setLineItemLogs(updatedlineItemsList);			
				
			}
		}
		return auditData;
	}
	
	public String getAssesmentName(Integer assesmentID){
		StringBuffer sbAuditFreq = new StringBuffer(3200);
		sbAuditFreq.append("SELECT name ");
		sbAuditFreq.append(" FROM assesment_type");
		sbAuditFreq.append(" WHERE id = ?");
		String name = null;
		
		try {
			name = getSimpleJdbcTemplate().queryForObject(sbAuditFreq.toString(), String.class,
					new Object[] { assesmentID });
		} catch (EmptyResultDataAccessException e) {

		}
		return name;
	}
	
	public Integer getAssesmentIdByThiId(Integer ThiId){
		StringBuffer sbAuditFreq = new StringBuffer(3200);
		sbAuditFreq.append("SELECT assesment_type_id ");
		sbAuditFreq.append(" FROM thi_score");
		sbAuditFreq.append(" WHERE id = ?");
		Integer name = null;
		
		try {
			name = Integer.parseInt(getSimpleJdbcTemplate().queryForObject(sbAuditFreq.toString(), String.class,
					new Object[] { ThiId }));
		} catch (EmptyResultDataAccessException e) {

		}
		return name;
	}
	
	

	@Override
	@SuppressWarnings("deprecation")
	public ProjectAuditor getProjectAuditor(Integer projectId, Date auditCycleDate) {
		// find the audit frequency to set the from date and to date
		StringBuffer sbAuditFreq = new StringBuffer(3200);
		sbAuditFreq.append("SELECT id, audit_freq");
		sbAuditFreq.append(" FROM project");
		sbAuditFreq.append(" WHERE id = ?");
		Project proj = null;
		try {
			proj = getSimpleJdbcTemplate().queryForObject(sbAuditFreq.toString(), new ProjectMapper(),
					new Object[] { projectId });
		} catch (EmptyResultDataAccessException e) {

		}
		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		System.out.println(auditCycleDate.toString());
		Date startsOn = auditCycleDate;
		Date endsOn = null;
		if (proj != null) {
			if (proj.getAuditFrequency() == 1) {
				endsOn = DateUtils.addMonths(startsOn, 1);
			} else if (proj.getAuditFrequency() == 2) {
				int ctMonth = auditCycleDate.getMonth() + 1;
				if (ctMonth % 2 == 0) {
					startsOn = DateUtils.addMonths(auditCycleDate, -1);
				}
				endsOn = DateUtils.addMonths(startsOn, 2);
			}
		}
		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date completionDate = new java.sql.Date(endsOn.getTime());
		logger.info("Completion day - date :: " + completionDate);
		System.out.println("project Id: " + projectId);
		ProjectAuditor projectAuditor = null;
		try {
			StringBuffer sb = new StringBuffer(3200);
			sb.append("SELECT id, project_id, auditor_id, audit_date, audit_complete");
			sb.append(" FROM project_auditor_mapping");
			sb.append(" WHERE project_id = ? AND ( audit_date >= ? AND audit_date < ?)");
			// sb.append(" and audit_complete = 0 order by audit_date desc limit
			// 1");
			projectAuditor = getSimpleJdbcTemplate().queryForObject(sb.toString(), new ProjectAuditorMapper(),
					new Object[] { projectId, beginDate, completionDate });
		} catch (EmptyResultDataAccessException e) {

		}
		return projectAuditor;
	}

	@Override
	public void updateThiScore(ThiScore score) {
		// getJdbcTemplate().
		// getSimpleJdbcTemplate().
		logger.info("Saving THI: " + score.getId());
		int count = getSimpleJdbcTemplate().update(
				"UPDATE thi_score SET overall_score = :overall_score, comments = :comments, recommendations = :recommendations, last_modified_by = :last_modified_by, last_modified_on = NOW() WHERE id=:id",
				new MapSqlParameterSource().addValue("overall_score", score.getOverallScore())
						.addValue("comments", score.getComments())
						.addValue("last_modified_by", score.getLastModifiedBy())
						.addValue("recommendations", score.getRecommendations()).addValue("id", score.getId()));
		logger.info("Rows affected: " + count);

		saveAssesmentGroupScore(score);

	}

	private void saveAssesmentGroupScore(ThiScore score) {
		for (AssesmentGroupScore groupScore : score.getAssesmentGroupScores()) {
			groupScore.setLastModifiedBy(score.getLastModifiedBy());
			updateAssesmentGroupScore(groupScore);
			saveLineItemLogs(groupScore);
		}
		logger.info("Updated Group Scores");
	}

	public void updateAssesmentGroupScore(AssesmentGroupScore groupScore) {
		getSimpleJdbcTemplate().update(
				"UPDATE thi_group_score SET score = :score, last_modified_by = :last_modified_by, last_modified_on = NOW() WHERE id = :id",
				new MapSqlParameterSource().addValue("score", groupScore.getScore())
						.addValue("last_modified_by", groupScore.getLastModifiedBy())
						.addValue("id", groupScore.getId()));
		
		for(LineItemLog lineitemlog: groupScore.getLineItemLogs()){
			getSimpleJdbcTemplate().update(
					"UPDATE thi_line_item_log SET score = :score WHERE id = :id",
					new MapSqlParameterSource().addValue("score", lineitemlog.getScore())
							.addValue("id", lineitemlog.getId()));
		}
		
	}

	private void saveLineItemLogs(AssesmentGroupScore groupScore) {
		for (LineItemLog lineItemLog : groupScore.getLineItemLogs()) {
			lineItemLog.setLastModifiedBy(groupScore.getLastModifiedBy());
			updateLineItemLogs(lineItemLog);
		}
		logger.info("Updated Lineitem Logs");
	}

	public void updateLineItemLogs(LineItemLog lineItemLog) {
		getSimpleJdbcTemplate().update(
				"UPDATE thi_line_item_log SET comments = :comments, last_modified_by = :last_modified_by, last_modified_on = NOW() WHERE id = :id",
				new MapSqlParameterSource().addValue("comments", lineItemLog.getComments())
						.addValue("last_modified_by", lineItemLog.getLastModifiedBy())
						.addValue("id", lineItemLog.getId()));
	}

	@Override
	public void insertThiScore(ThiScore score) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", score.getProjectId());
		parameters.addValue("auditor_id", score.getAuditorId());
		parameters.addValue("audit_date", new Date());
		parameters.addValue("audit_cycle_date", new Date());
		parameters.addValue("overall_score", score.getOverallScore());
		parameters.addValue("comments", score.getComments());
		parameters.addValue("recommendations", score.getRecommendations());
		parameters.addValue("project_owner_id", this.getProjectOwnerId(score.getProjectId()));
		parameters.addValue("created_by", score.getCreatedBy());
		parameters.addValue("created_on", new Date());
		parameters.addValue("last_modified_by", score.getLastModifiedBy());
		parameters.addValue("last_modified_on", new Date());
		parameters.addValue("assesment_type_id", score.getAssesmentType());

		// int id = new SimpleJdbcInsert(getJdbcTemplate()).

		SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());

		insert.withTableName("thi_score");
		insert.usingColumns("project_id", "auditor_id", "audit_date", "audit_cycle_date", "overall_score", "comments",
				"recommendations", "project_owner_id", "created_by", "created_on", "last_modified_by",
				"last_modified_on", "assesment_type_id");
		insert.usingGeneratedKeyColumns("id");

		Integer thiScoreId = insert.executeAndReturnKey(parameters).intValue();
		score.setId(thiScoreId);

		createGroupScores(score);

	}

	private void createGroupScores(ThiScore thiScore) {
		for (AssesmentGroupScore groupScore : thiScore.getAssesmentGroupScores()) {
			insertGroupScore(groupScore, thiScore);

		}
	}

	private void insertGroupScore(AssesmentGroupScore groupScore, ThiScore thisScore) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", thisScore.getProjectId());
		parameters.addValue("thi_score_id", thisScore.getId());
		parameters.addValue("assessment_group_id", groupScore.getAssesmentGroupId());
		parameters.addValue("score", groupScore.getScore());
		parameters.addValue("created_by", thisScore.getCreatedBy());
		parameters.addValue("created_on", new Date());
		parameters.addValue("last_modified_by", thisScore.getLastModifiedBy());
		parameters.addValue("last_modified_on", new Date());

		SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());

		insert.withTableName("thi_group_score");
		insert.usingColumns("thi_score_id", "project_id", "assessment_group_id", "score", "created_by", "created_on",
				"last_modified_by", "last_modified_on");
		insert.usingGeneratedKeyColumns("id");

		Double groupScoreId = insert.executeAndReturnKey(parameters).doubleValue();
		groupScore.setId(groupScoreId.intValue());
		// calling the function to store the logs and seperate score vkp
		createLineItemLogs(groupScore, thisScore);

	}

	private void createLineItemLogs(AssesmentGroupScore groupScore, ThiScore thiScore) {
		for (LineItemLog lineItemLog : groupScore.getLineItemLogs()) {
			insertLineItemLogs(lineItemLog, groupScore, thiScore);

		}
	}

	private void insertLineItemLogs(LineItemLog lineItemLog, AssesmentGroupScore groupScore, ThiScore score) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("thi_group_score_id", groupScore.getId());
		parameters.addValue("thi_score_id", score.getId());
		parameters.addValue("assesment_line_item_id", lineItemLog.getLineItemId());
		parameters.addValue("comments", lineItemLog.getComments());
		parameters.addValue("score", lineItemLog.getScore());
		parameters.addValue("created_by", score.getCreatedBy());
		parameters.addValue("created_on", new Date());
		parameters.addValue("last_modified_by", score.getLastModifiedBy());
		parameters.addValue("last_modified_on", new Date());

		SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());

		insert.withTableName("thi_line_item_log");
		insert.usingColumns("thi_group_score_id", "thi_score_id", "assesment_line_item_id", "comments", "score", "created_by",
				"created_on", "last_modified_by", "last_modified_on");
		insert.usingGeneratedKeyColumns("id");

		Integer lineItemLogId = insert.executeAndReturnKey(parameters).intValue();
		lineItemLog.setId(lineItemLogId);

	}

	private static class AssesmentTypeMapper implements ParameterizedRowMapper<AssesmentType> {
		@Override
		public AssesmentType mapRow(ResultSet rs, int rowNum) throws SQLException {
			AssesmentType assesmentType = new AssesmentType();
			assesmentType.setId(rs.getInt("id"));
			assesmentType.setName(rs.getString("name"));
			return assesmentType;
		}

	}

	@Override
	public List<AssesmentType> getAssesmentTypes() {
		@SuppressWarnings("deprecation")
		List<AssesmentType> assesmentTyps = getSimpleJdbcTemplate().query("SELECT id, name FROM assesment_type",
				new AssesmentTypeMapper());
		return assesmentTyps;
	}

	@Override
	public List<String> getAssesmentCriteria(Integer assesmentGroupId) {
		@SuppressWarnings("deprecation")
		List<String> assesmentTyps = getSimpleJdbcTemplate().query(
				"SELECT group_checklist FROM assesment_group_checklist WHERE assesment_group_id = ?",
				new ParameterizedRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("group_checklist");
					}
				}, assesmentGroupId);
		return assesmentTyps;
	}

	@Override
	public void setAuditStatus(Boolean auditComplete, Integer projectId, Integer auditorId, Date auditCycleDate) {

		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		Date startsOn = auditCycleDate;
		int ctMonth = auditCycleDate.getMonth() + 1;
		if (ctMonth % 2 == 0) {
			startsOn = DateUtils.addMonths(auditCycleDate, -1);
		}
		Date endsOn = DateUtils.addMonths(startsOn, 2);

		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date completionDate = new java.sql.Date(endsOn.getTime());
		logger.info("Completion day - date :: " + completionDate);
		int count = getSimpleJdbcTemplate().update(
				"UPDATE project_auditor_mapping SET audit_complete = :audit_complete WHERE project_id = :project_id AND auditor_id = :auditor_id AND ( audit_date >= :audit_start_date AND audit_date < :audit_end_date)",
				new MapSqlParameterSource().addValue("audit_complete", auditComplete).addValue("project_id", projectId)
						.addValue("auditor_id", auditorId).addValue("audit_start_date", beginDate)
						.addValue("audit_end_date", completionDate));
		logger.info("Rows affected: " + count);

	}

	@Override
	public String getProjectOwnerId(Integer projectId) {
		String ownerId = getSimpleJdbcTemplate()
				.queryForObject("SELECT owner_user_id FROM project proj WHERE proj.id = ?", String.class, projectId);
		return ownerId;
	}

	@Override
	public String getAssessmentGroupName(Integer assesmentGroupId) {
		String assesmentGroupName = getSimpleJdbcTemplate()
				.queryForObject("SELECT  group_name FROM assesment_group WHERE id = ?", String.class, assesmentGroupId);
		return assesmentGroupName;
	}

	// insert project auditee acenos into project_auditee_mapping table
	public void insertAuditee(int projectId, String[] auditee, String userAceNo) {
		StringBuffer sbAuditId = new StringBuffer(3200);
		sbAuditId.append("select max(id) from project_auditor_mapping where project_id=" + projectId);

		Integer auditId = getSimpleJdbcTemplate().queryForInt(sbAuditId.toString());

		// delete the existing auditee names
		StringBuffer sbDeleteAuditee = new StringBuffer(3200);
		sbDeleteAuditee.append("delete from project_auditee_mapping where audit_id=" + auditId);

		int delcount = getSimpleJdbcTemplate().update(sbDeleteAuditee.toString());

		StringBuffer sbScoreId = new StringBuffer(3200);
		sbScoreId.append("select max(id) from thi_score where project_id=" + projectId);

		Integer scoreId = getSimpleJdbcTemplate().queryForInt(sbScoreId.toString());
		for (int i = 0; i < auditee.length; ++i) {
			StringBuffer sbInsertAuditee = new StringBuffer(3200);
			sbInsertAuditee.append("insert into project_auditee_mapping(");
			sbInsertAuditee.append(" audit_id,auditee_name,created_by,created_on");
			sbInsertAuditee.append(" ,lastmodified_by,lastmodified_on,score_id)");
			sbInsertAuditee.append(" values(" + auditId + ",'" + auditee[i] + "','" + userAceNo + "',now(),'"
					+ userAceNo + "',now()," + scoreId + ")");

			int count = getSimpleJdbcTemplate().update(sbInsertAuditee.toString());
		}

	}

	private static class AuditeeNameMapper implements ParameterizedRowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("auditee_name");
		}

	}

	// insert project auditee names into project_auditee_mapping table
	public List<String> getProjectAuditee(int projectId) {
		StringBuffer sbAuditId = new StringBuffer(3200);
		sbAuditId.append("select max(id) from project_auditor_mapping where project_id=" + projectId);

		Integer auditId = getSimpleJdbcTemplate().queryForInt(sbAuditId.toString());
		StringBuffer sbSelectAuditee = new StringBuffer(3200);
		sbSelectAuditee.append("select auditee_name from project_auditee_mapping");
		sbSelectAuditee.append(" where audit_id=" + auditId);
		List<String> auditeeNames = getSimpleJdbcTemplate().query(sbSelectAuditee.toString(), new AuditeeNameMapper());

		return auditeeNames;

	}
/* To read weitage from tbl
	@SuppressWarnings("deprecation")
	@Override
	public List<LineItemWeight> getAssessmentScore(Integer assesmentType) {

		StringBuffer assessmentScores = new StringBuffer(3200);
		assessmentScores.append("select ali.id as id,ali.line_item_text as texts,ag.group_name as groups,ali.assesment_type_id as type,ali.percentage as percentage "
				+ "from assesment_line_item ali,assesment_group ag "
				+ "where ali.assesment_group_id=ag.id and ali.assesment_type_id="+ assesmentType+" order by ag.id,ali.id");
		
		List<LineItemWeight> assesmentTypsScore = getSimpleJdbcTemplate().query(assessmentScores.toString(),
				new AssesmentTypeScores());
		return assesmentTypsScore;
		

	}
	
	private static class AssesmentTypeScores implements ParameterizedRowMapper<LineItemWeight> {
		@Override
		public LineItemWeight mapRow(ResultSet rs, int rowNum) throws SQLException {
			LineItemWeight assesmentTypeRes = new LineItemWeight();
			assesmentTypeRes.setId(rs.getInt("id"));
			assesmentTypeRes.setAssessmentGroupName(rs.getString("groups"));
			assesmentTypeRes.setAssesmentType(rs.getInt("type"));
			assesmentTypeRes.setLineItemText(rs.getString("texts"));
			assesmentTypeRes.setWeitage(rs.getInt("percentage"));
			
			return assesmentTypeRes;
		}
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public List<LineItemWeight> getAssessmentLists(Integer id, String groupName, Integer assesmentType) {
		StringBuffer assessment = new StringBuffer(3200);
		assessment.append("select ali.id as id,ali.line_item_text as texts,ag.group_name as groups,ali.assesment_type_id as type,ali.percentage as percentage "
				+ "from assesment_line_item ali,assesment_group ag "
				+ "where ali.assesment_group_id=ag.id and ali.assesment_type_id="+assesmentType+" and ag.group_name='"+groupName+"' "
				+ "order by ag.id,ali.id");
		
		List<LineItemWeight> assesmentLists = getSimpleJdbcTemplate().query(assessment.toString(),
				new AssesmentTypeLists());
		return assesmentLists;
	}
	
	private static class AssesmentTypeLists implements ParameterizedRowMapper<LineItemWeight> {
		@Override
		public LineItemWeight mapRow(ResultSet rs, int rowNum) throws SQLException {
			LineItemWeight assesmentTypeRes = new LineItemWeight();
			assesmentTypeRes.setId(rs.getInt("id"));
			assesmentTypeRes.setAssessmentGroupName(rs.getString("groups"));
			assesmentTypeRes.setAssesmentType(rs.getInt("type"));
			assesmentTypeRes.setLineItemText(rs.getString("texts"));
			assesmentTypeRes.setWeitage(rs.getInt("percentage"));
			
			return assesmentTypeRes;
		}

	}
*/
	public Properties getScoreproperties() {
		return scoreproperties;
	}

	public void setScoreproperties(Properties scoreproperties) {
		this.scoreproperties = scoreproperties;
	}

	/* setting updating vkp
	@Override
	public int updateLineItem(int id, String groupName, Integer assessmentType, Integer percentage) {
	
		int count = getSimpleJdbcTemplate().update(
				"UPDATE assesment_line_item SET percentage = :weight WHERE id=:id",
				new MapSqlParameterSource().addValue("id", id).addValue("weight", percentage));
		
		return count;
		
	}
	*/




}