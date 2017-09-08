package com.aspire.thi.service;

import java.io.StringReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.repository.AuditorRepository;
import com.aspire.thi.utils.AppContext;
import com.aspire.thi.ws.client.IDMWebService;


public class AuditorManager {
	
	private static final Logger LOGGER = Logger.getLogger(AuditorManager.class); 
	private AuditorRepository auditorDao;
	
	public List<Auditor> getAuditorList() {
		return auditorDao.getAuditorList();
	}
	
	public Auditor getAuditorByID(int id) {
		return auditorDao.getAuditorByID(id);
	}

	public AuditorRepository getAuditorDao() {
		return auditorDao;
	}

	public void setAuditorDao(AuditorRepository auditorDao) {
		this.auditorDao = auditorDao;
	}

	public List<ProjectAuditor> getProjectAuditorList() {
		return auditorDao.getProjectAuditorList();
	}
	
	public ProjectAuditor getProjectAuditorByID(int id) throws SQLException{
		return auditorDao.getProjectAuditorByID(id);
	}
	
	public boolean saveProjectAuditor(ProjectAuditor projAuditor) throws SQLException{
		return auditorDao.saveProjectAuditor(projAuditor);
	}
	
	public List<ProjectAuditorHelper> searchProjectAuditors(String thiName, Date startDate, Date endDate, boolean auditComplete) {
		return auditorDao.searchProjectAuditors(thiName, startDate, endDate, auditComplete);
	}
	
	public List<ProjectAuditorHelper> getProjectAuditorsByProjectId(String projectId) {
		return auditorDao.getProjectAuditorsByProjectId(projectId);
	}

	public void createAuditor(Auditor auditor) {
		auditorDao.createAuditor(auditor);
	}
	
	public Auditor loadUser(String aceNumber){
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(Calendar.getInstance().getTime());
		String empXML = idmWs.GetEmployeeDetail(date, aceNumber);
		Auditor auditor = new Auditor();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML));
			Document activeEmployee = builder.parse(is);
			NodeList nodes = activeEmployee.getElementsByTagName("Employee");
			Element node = (Element) nodes.item(0);
			auditor.setAceNo(node.getAttribute("ACEID"));
			auditor.setName(((Element)node.getChildNodes().item(0)).getAttribute("UserName"));
			auditor.setEmail(node.getChildNodes().item(1).getTextContent());
			auditor.setDeptID(((Element)node.getChildNodes().item(7)).getAttribute("Identifier"));
			auditor.setDeptName(node.getChildNodes().item(7).getTextContent());
			auditor.setCreatedBy("ACE1413");
			auditor.setLastModifiedBy("ACE1413");
		} catch (Exception e) {
			LOGGER.error("Exception while building User Detail Object ", e);
		}
		LOGGER.info("User Detail Object is " + auditor);
		return auditor;
	}
	
	public int getProjectAuditorId(int projectId){
		return auditorDao.getProjectAuditorId(projectId);
	}
	
	public String removeAuditor(String aceNo, String lastModifiedBy){
		return auditorDao.removeAuditor(aceNo, lastModifiedBy);
	}
}
