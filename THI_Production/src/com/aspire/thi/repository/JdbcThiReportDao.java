package com.aspire.thi.repository;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import com.aspire.thi.utils.AppContext;
import com.aspire.thi.ws.client.IDMWebService;

import com.aspire.thi.domain.ThiReport;

public class JdbcThiReportDao extends SimpleJdbcDaoSupport implements ThiReportRepository {

    /** Logger for this class and subclasses */
	protected final Logger logger = Logger.getLogger(getClass());

	private static class ThiReportMapper implements RowMapper<ThiReport> {
		@Override
		public ThiReport mapRow(ResultSet rs, int rowNum) throws SQLException {
			ThiReport report = new ThiReport();
			report.setThiScoreId(rs.getInt("id"));
			report.setProjectName(rs.getString("prj_name"));
			report.setAssesmentType(rs.getString("assesment_type"));
			report.setOverallScore(rs.getDouble("overall_score"));
			report.setAuditDate(rs.getDate("audit_date"));
			return report;
		}

	}
	
	private static class ThiOrgReportMapper implements RowMapper<ThiReport> {
		@Override
		public ThiReport mapRow(ResultSet rs, int rowNum) throws SQLException {
			ThiReport report = new ThiReport();
			report.setThiScoreId(rs.getInt("id"));
			report.setProjectName(rs.getString("prj_name"));
			report.setAssesmentType(rs.getString("assesment_type"));
			report.setOverallScore(rs.getDouble("overall_score"));
			return report;
		}

	}
	
	@Override
	public List<ThiReport> getOrgThiReport(Integer duId, Date auditCycleDate) {
		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		Date startsOn = auditCycleDate;
		int ctMonth = auditCycleDate.getMonth() + 1;
		if(ctMonth % 2 == 0){
			startsOn = DateUtils.addMonths(auditCycleDate, -1);
		}
		Date endsOn = DateUtils.addMonths(startsOn, 2);
		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date endDate = new java.sql.Date(endsOn.getTime());
		logger.info("End day - date :: " + endDate);
		
		List<ThiReport> report = null;
		String query = "SELECT tscore.id,overall_score,ast.name AS assesment_type, prj.prj_name FROM thi_score tscore "
				+ "INNER JOIN project prj ON tscore.project_id = prj.id INNER JOIN assesment_type ast ON tscore.assesment_type_id = ast.id "
				+ "INNER JOIN department dept ON prj.dept_id = dept.id "
				+ "INNER JOIN project_auditor_mapping auditMap ON tscore.project_id = auditMap.project_id AND tscore.auditor_id = auditMap.auditor_id "
				+ "WHERE dept_id = ? AND ( audit_cycle_date >= ? AND audit_cycle_date < ?) AND ( auditMap.audit_date >= ? AND auditMap.audit_date < ?) "
				+ "AND auditMap.audit_complete  = 1 ORDER BY assesment_type_id";
		try {
			report = getSimpleJdbcTemplate().query(query, new ThiOrgReportMapper(),
					new Object[] { duId, beginDate, endDate, beginDate, endDate });
			if (report != null && report.isEmpty() == Boolean.FALSE) {
				for (ThiReport thiReport : report) {
					thiReport.setAssesmentGroupScore(this.getAssesmentGroupScores(thiReport.getThiScoreId()));
				}
			}
		} catch (EmptyResultDataAccessException e) {

		}
		return report;
	}


	@Override
	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate) {
		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		Date startsOn = auditCycleDate;
		startsOn = DateUtils.addMonths(auditCycleDate, -2);
		int ctMonth = auditCycleDate.getMonth() + 1;
		Date endsOn;
		if(ctMonth % 2 == 0){
			startsOn = DateUtils.addMonths(auditCycleDate, -5);
			endsOn = DateUtils.addMonths(auditCycleDate, 1);
		}else{
			startsOn = DateUtils.addMonths(auditCycleDate, -4);
			endsOn = DateUtils.addMonths(auditCycleDate, 2);
		}
		//Date endsOn = DateUtils.addMonths(auditCycleDate, 2);
		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date endDate = new java.sql.Date(endsOn.getTime());
		logger.info("End day - date :: " + endDate);
		//get the project IDs
		String prosIds = getChildDepartments(duId);
		logger.info("prosids:"+prosIds);
		StringBuffer sb = new StringBuffer(6400);
		sb.append("select id from project where dept_id in (select");
		sb.append(" id from department where pros_dept_id in("+prosIds+"))");
		List<Integer> proIds = getSimpleJdbcTemplate().query(sb.toString(), new ProjectIdsMapper());
		List<ThiReport> report = new ArrayList<ThiReport>(proIds.size());
		for(Integer projectId: proIds){
		logger.info("project Id:"+projectId);
		
		Date incompleteAuditDate = null;
		java.sql.Date inComAuditDate = null;
		Date inCompleteStartDate = null;
		Date inCompleteEndDate = null;
		try {
			inComAuditDate = getSimpleJdbcTemplate().queryForObject(
					"SELECT audit_date FROM project_auditor_mapping" +
					" WHERE project_id = ? AND audit_complete = 0",java.sql.Date.class, 
					projectId);
		} catch (EmptyResultDataAccessException e) {
			//e.printStackTrace();
		}
		if(inComAuditDate!=null){
			incompleteAuditDate = new Date();
			incompleteAuditDate.setTime(inComAuditDate.getTime());
			System.out.println("INcomplete audit date:"+incompleteAuditDate.toString());
		}
		Integer incompletAuditThiScore = -1;
		Integer auditFrequency = 0;
		if(incompleteAuditDate!=null){
			auditFrequency = getSimpleJdbcTemplate().queryForInt(
						"SELECT audit_freq FROM project" +
						" WHERE id = "+projectId);
			if(auditFrequency == 1){
				Calendar cal = Calendar.getInstance();
				cal.setTime(incompleteAuditDate);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				inCompleteStartDate = cal.getTime();
				//System.out.println("incompletestartdate:"+inCompleteStartDate.toString());
				cal.add(Calendar.MONTH, 1);
				inCompleteEndDate = cal.getTime();
				//System.out.println("incompleteenddate:"+inCompleteEndDate.toString());
			}else if(auditFrequency == 2){
				Calendar cal = Calendar.getInstance();
				cal.setTime(incompleteAuditDate);
				int auditMonth = cal.get(Calendar.MONTH);
				auditMonth = auditMonth + 1;
				if(auditMonth%2 == 1){
					cal.set(Calendar.DAY_OF_MONTH, 1);
					inCompleteStartDate = cal.getTime();
					cal.add(Calendar.MONTH, 2);
					inCompleteEndDate = cal.getTime();
				}else{
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal.add(Calendar.MONTH, 1);
					inCompleteEndDate = cal.getTime();
					cal.add(Calendar.MONTH, -2);
					inCompleteStartDate = cal.getTime();
				}
				
			}
		}
		
		if((inCompleteStartDate!=null) &&(inCompleteEndDate!=null)){
			try{
				StringBuffer sb1 = new StringBuffer(3200);
				sb1.append("select id from thi_score where project_id=:projectId");
				sb1.append(" and (audit_date >= :startson and audit_date < :endson)");
				MapSqlParameterSource map = new MapSqlParameterSource();
				map.addValue("projectId",projectId);
				map.addValue("startson",inCompleteStartDate);
				map.addValue("endson", inCompleteEndDate);
				incompletAuditThiScore = getSimpleJdbcTemplate().queryForInt(sb1.toString(), map);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
			StringBuffer sbQuery = new StringBuffer(6400);
			sbQuery.append("SELECT tscore.id,overall_score,ast.name AS assesment_type, prj.prj_name, tscore.audit_date FROM thi_score tscore");
			sbQuery.append(" INNER JOIN project prj ON tscore.project_id = prj.id INNER JOIN assesment_type ast ON tscore.assesment_type_id = ast.id");
			sbQuery.append(" WHERE  audit_cycle_date < ? AND tscore.audit_date < ?");
			if(incompletAuditThiScore != -1){
				sbQuery.append(" AND tscore.id <> ?");
			}
			sbQuery.append(" AND prj.id = ? ORDER BY prj.prj_name asc,tscore.audit_date desc LIMIT 3");
			List<ThiReport> tReport = null;
			try {
				if(incompletAuditThiScore != -1){
					tReport = getSimpleJdbcTemplate().query(sbQuery.toString(), new ThiReportMapper(),
							new Object[] { endDate, endDate, incompletAuditThiScore, projectId });
				}else{
					tReport = getSimpleJdbcTemplate().query(sbQuery.toString(), new ThiReportMapper(),
							new Object[] { endDate, endDate, projectId });
				}
				if (tReport != null && tReport.isEmpty() == Boolean.FALSE) {
					for (ThiReport thiReport : tReport) {
						thiReport.setAssesmentGroupScore(this.getAssesmentGroupScores(thiReport.getThiScoreId()));
					}
				}
			} catch (EmptyResultDataAccessException e) {
	
			}
			for(ThiReport thiReport: tReport){
				report.add(thiReport);
			}
		}
		return report;
	}
	
	@Override
	public List<ThiReport> getDUThiReport(Integer duId, Date auditCycleDate, Date toDate) {
		auditCycleDate = DateUtils.setDays(auditCycleDate, 1);
		Date startsOn = auditCycleDate;
		toDate = DateUtils.setDays(toDate, 1);
		Date endsOn = DateUtils.addMonths(toDate, 1);
		java.sql.Date beginDate = new java.sql.Date(startsOn.getTime());
		logger.info("Begining day - date :: " + beginDate);
		java.sql.Date endDate = new java.sql.Date(endsOn.getTime());
		logger.info("End day - date :: " + endDate);
		//get the project IDs
		String prosIds = getChildDepartments(duId);
		logger.info("prosids:"+prosIds);
		StringBuffer sb = new StringBuffer(6400);
		sb.append("select id from project where dept_id in (select");
		sb.append(" id from department where pros_dept_id in("+prosIds+"))");
		List<Integer> proIds = getSimpleJdbcTemplate().query(sb.toString(), new ProjectIdsMapper());
		List<ThiReport> report = new ArrayList<ThiReport>(proIds.size());
		for(Integer projectId: proIds){
		logger.info("project Id:"+projectId);
		
		Date incompleteAuditDate = null;
		java.sql.Date inComAuditDate = null;
		Date inCompleteStartDate = null;
		Date inCompleteEndDate = null;
		try {
			inComAuditDate = getSimpleJdbcTemplate().queryForObject(
					"SELECT audit_date FROM project_auditor_mapping" +
					" WHERE project_id = ? AND audit_complete = 0",java.sql.Date.class, 
					projectId);
		} catch (EmptyResultDataAccessException e) {
			//e.printStackTrace();
		}
		if(inComAuditDate!=null){
			incompleteAuditDate = new Date();
			incompleteAuditDate.setTime(inComAuditDate.getTime());
			System.out.println("INcomplete audit date:"+incompleteAuditDate.toString());
		}
		Integer incompletAuditThiScore = -1;
		Integer auditFrequency = 0;
		if(incompleteAuditDate!=null){
			auditFrequency = getSimpleJdbcTemplate().queryForInt(
						"SELECT audit_freq FROM project" +
						" WHERE id = "+projectId);
			if(auditFrequency == 1){
				Calendar cal = Calendar.getInstance();
				cal.setTime(incompleteAuditDate);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				inCompleteStartDate = cal.getTime();
				//System.out.println("incompletestartdate:"+inCompleteStartDate.toString());
				cal.add(Calendar.MONTH, 1);
				inCompleteEndDate = cal.getTime();
				//System.out.println("incompleteenddate:"+inCompleteEndDate.toString());
			}else if(auditFrequency == 2){
				Calendar cal = Calendar.getInstance();
				cal.setTime(incompleteAuditDate);
				int auditMonth = cal.get(Calendar.MONTH);
				auditMonth = auditMonth + 1;
				if(auditMonth%2 == 1){
					cal.set(Calendar.DAY_OF_MONTH, 1);
					inCompleteStartDate = cal.getTime();
					cal.add(Calendar.MONTH, 2);
					inCompleteEndDate = cal.getTime();
				}else{
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal.add(Calendar.MONTH, 1);
					inCompleteEndDate = cal.getTime();
					cal.add(Calendar.MONTH, -2);
					inCompleteStartDate = cal.getTime();
				}
				
			}
		}
		
		if((inCompleteStartDate!=null) &&(inCompleteEndDate!=null)){
			try{
				StringBuffer sb1 = new StringBuffer(3200);
				sb1.append("select id from thi_score where project_id=:projectId");
				sb1.append(" and (audit_date >= :startson and audit_date < :endson)");
				MapSqlParameterSource map = new MapSqlParameterSource();
				map.addValue("projectId",projectId);
				map.addValue("startson",inCompleteStartDate);
				map.addValue("endson", inCompleteEndDate);
				incompletAuditThiScore = getSimpleJdbcTemplate().queryForInt(sb1.toString(), map);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
			StringBuffer sbQuery = new StringBuffer(6400);
			sbQuery.append("SELECT tscore.id,overall_score,ast.name AS assesment_type, prj.prj_name, tscore.audit_date FROM thi_score tscore");
			sbQuery.append(" INNER JOIN project prj ON tscore.project_id = prj.id INNER JOIN assesment_type ast ON tscore.assesment_type_id = ast.id");
			sbQuery.append(" WHERE  (audit_cycle_date >= ? and audit_cycle_date < ?) AND (tscore.audit_date >= ? and tscore.audit_date < ?)");
			if(incompletAuditThiScore != -1){
				sbQuery.append(" AND tscore.id <> ?");
			}
			sbQuery.append(" AND prj.id = ? ORDER BY prj.prj_name asc,tscore.audit_date desc LIMIT 3");
			List<ThiReport> tReport = null;
			try {
				if(incompletAuditThiScore != -1){
					tReport = getSimpleJdbcTemplate().query(sbQuery.toString(), new ThiReportMapper(),
							new Object[] { beginDate, endDate, beginDate, endDate, incompletAuditThiScore, projectId });
				}else{
					tReport = getSimpleJdbcTemplate().query(sbQuery.toString(), new ThiReportMapper(),
							new Object[] { beginDate, endDate, beginDate, endDate, projectId });
				}
				if (tReport != null && tReport.isEmpty() == Boolean.FALSE) {
					for (ThiReport thiReport : tReport) {
						thiReport.setAssesmentGroupScore(this.getAssesmentGroupScores(thiReport.getThiScoreId()));
					}
				}
			} catch (EmptyResultDataAccessException e) {
	
			}
			for(ThiReport thiReport: tReport){
				report.add(thiReport);
			}
		}
		return report;
	}

	private static class AssesmentGroupScoreMapper implements RowMapper<Map<String, Double>> {
		@Override
		public Map<String, Double> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, Double> groupScore = new LinkedHashMap<String, Double>();
			do {
				groupScore.put(rs.getString("group_name"), rs.getDouble("score"));

			} while (rs.next());
			return groupScore;
		}
	}

	public Map<String, Double> getAssesmentGroupScores(Integer thiScoreId) {
		Map<String, Double> assesmentGroupScores = getSimpleJdbcTemplate()
				.queryForObject(
						"SELECT score, group_name FROM thi_group_score gscore INNER JOIN assesment_group agroup ON gscore.assessment_group_id = agroup.id WHERE thi_score_id = ?",
						new AssesmentGroupScoreMapper(), thiScoreId);
		return assesmentGroupScores;
	}
	

	private static class ProjectIdsMapper implements RowMapper<Integer> {
		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt("id");
		}
	}
	
	private String getChildDepartments(Integer duId){
		StringBuffer sb = new StringBuffer(3200);
		sb.append("select pros_dept_id from department where id=?");
		String deptId = getSimpleJdbcTemplate().queryForObject(sb.toString(),
				String.class,duId);
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String empXML = idmWs.GetAllDepartment();
		logger.info(empXML);
		List<String> resultIds = new ArrayList<String>();
		resultIds.add(deptId);
	
		/*get all the child department*/
		List<String> listChildDeptId = new ArrayList<String>();
		listChildDeptId.add(deptId);
				
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(empXML));
		Document activeCustomer = builder.parse(is);
		NodeList nodes = activeCustomer.getElementsByTagName("Department");
		getChildDeptIds(nodes,listChildDeptId,resultIds);
		logger.info("Department:"+nodes.getLength());
		StringBuffer sbIds = new StringBuffer(6400);
		int i=0;
		for(i=0;i<resultIds.size()-1;++i){
			sbIds.append("'"+resultIds.get(i)+"',");
			logger.info(i+" : "+resultIds.get(i));
		}
		sbIds.append("'"+resultIds.get(i)+"'");
		return sbIds.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private void getChildDeptIds(NodeList nodes,List<String> childDeptIds, List<String> resultIds){
		boolean flag = false;
		List<String> newChildDeptIds = new ArrayList<String>(83);
		try{
		for(int i=0;i<nodes.getLength();++i){
			
			Element ele = (Element)nodes.item(i);
			Element ele1 = (Element)ele.getElementsByTagName("ParentDepartment").item(0);
			String id = ele1.getAttribute("Identifier");
			for(String id1:childDeptIds){
				if(id.equals(id1)){
					newChildDeptIds.add(ele.getAttribute("Identifier"));
					resultIds.add(ele.getAttribute("Identifier"));
					flag = true;
					break;
				}
			}
		}
		if(newChildDeptIds!=null)
		{
			for(String id : newChildDeptIds){
				logger.info("inside :"+id);
			}
		}
		if(flag){
			getChildDeptIds(nodes,newChildDeptIds,resultIds);
			return;
		}else{
			return;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}