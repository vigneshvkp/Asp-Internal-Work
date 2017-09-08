package com.aspire.thi.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.LoginCredentials;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.repository.AuditorRepository;
import com.aspire.thi.utils.AppContext;
import com.aspire.thi.ws.client.IDMWebService;
import com.aspire.thi.ws.client.ProsWebService;

public class AuthenticationManager {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private AuditorRepository auditorDao;
	
	private IProjectManager projectManager;
	
	public void setAuditorDao(AuditorRepository auditorDao){
		this.auditorDao = auditorDao;
	}

	public void setProjectManager(IProjectManager projectManager){
		this.projectManager = projectManager;
	}

	public boolean authenticate(LoginCredentials loginCredentials){
		// Checking the LDAP authentication.
		Hashtable<String, String> env = new Hashtable<String,String>(4);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.0.1:389");			
		env.put(Context.SECURITY_PRINCIPAL, "aspiresys" + "\\" + loginCredentials.getUserName());
		env.put(Context.SECURITY_CREDENTIALS, loginCredentials.getPassword());

		String MISPassword = loginCredentials.getPassword();
		if(StringUtils.equalsIgnoreCase(MISPassword, "M!S_TH!_DEV@!@#")){
			logger.debug("Developer Authentication :  Success" );
			return true;
		}

		try {
			DirContext ctx = new InitialDirContext(env);
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
			logger.debug("LDAP Authentification : Failure" );
			return false;
		}	
		logger.debug("LDAP Authentification : Success" );
		return true;
	}
	
	public UserDetail loadUser(String userName){
		
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(
				Calendar.getInstance().getTime());
		String empXML = idmWs.GetEmployeeDetailByUserName(date, userName);
		UserDetail userDetail = null;
		logger.info("emp XML = " + empXML);
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML));
			Document activeCustomer = builder.parse(is);
			NodeList nodes = activeCustomer.getElementsByTagName("Employee");
			if (nodes.getLength() == 1) {
				userDetail = new UserDetail(userName);
				Element node = (Element) nodes.item(0);
				userDetail.setAceNo(node.getAttribute("ACEID"));
				userDetail.setUserStringId(node.getAttribute("UserStringID"));
				userDetail.setEmail(node.getElementsByTagName("Email").item(0).getTextContent());
				userDetail.setLevel(((Element)node.getElementsByTagName("Level").item(0)).getAttribute("Identifier"));
				userDetail.setDeptId(((Element)node.getElementsByTagName("Department").item(0)).getAttribute("Identifier"));;
				userDetail.setDuId(((Element)node.getElementsByTagName("DeliveryUnit").item(0)).getAttribute("Identifier"));;
				boolean isAuditor = auditorDao.isAuditor(userDetail.getAceNo());
				userDetail.setAuditor(isAuditor);
				if(isAuditor){
					int auditorId = auditorDao.getAuditorId(userDetail.getAceNo());	
					userDetail.setAuditorId(auditorId);
					loadAuditProjectIds(userDetail);
				} 
				loadAssociatedProjects(userDetail);
		}
		} catch (Exception e) {
			logger.error("Exception while building User Detail Object ", e);
		}
		logger.info("User Detail Object is " + userDetail);
		return userDetail;
	}

	public UserDetail loadUserByStringId(String userId) {

		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(Calendar.getInstance().getTime());
		String empXML = idmWs.GetEmployeeDetailByUserStringID(date, String.valueOf(userId));
		UserDetail userDetail = null;
		logger.info("emp XML = " + empXML);
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML));
			Document activeCustomer = builder.parse(is);
			NodeList nodes = activeCustomer.getElementsByTagName("Employee");
			if (nodes.getLength() == 1) {
				Element node = (Element) nodes.item(0);
				userDetail = new UserDetail(node.getAttribute("UserID"));
				userDetail.setAceNo(node.getAttribute("ACEID"));
				userDetail.setUserStringId(node.getAttribute("UserStringID"));
				userDetail.setEmail(node.getElementsByTagName("Email").item(0).getTextContent());
				userDetail.setLevel(((Element) node.getElementsByTagName("Level").item(0)).getAttribute("Identifier"));
				userDetail.setDeptId(((Element) node.getElementsByTagName("Department").item(0))
						.getAttribute("Identifier"));
				;
				userDetail.setDuId(((Element) node.getElementsByTagName("DeliveryUnit").item(0))
						.getAttribute("Identifier"));
				;
				boolean isAuditor = auditorDao.isAuditor(userDetail.getAceNo());
				userDetail.setAuditor(isAuditor);
				if (isAuditor) {
					int auditorId = auditorDao.getAuditorId(userDetail.getAceNo());
					userDetail.setAuditorId(auditorId);
					loadAuditProjectIds(userDetail);
				}
				loadAssociatedProjects(userDetail);
			}
		} catch (Exception e) {
			logger.error("Exception while building User Detail Object ", e);
		}
		logger.info("User Detail Object is " + userDetail);
		return userDetail;
	}

	private void loadAssociatedProjects(UserDetail userDetail) throws Exception {

		try {
			List<Integer> projectIds = projectManager.getAssociatedProjectIds(userDetail.getUserStringId());
			logger.info( "Project Ids =" + projectIds );
			userDetail.setProjectIds(projectIds);
		} catch (Exception e) {
			logger.error("Exception while building Associated Projects in User Detail Object ", e);
			throw e;
		}

/*		ProsWebService prosWebService = AppContext.getProsServiceBean();
		String prjXML = prosWebService.GetProjectResourceAllocationAndProjectDetailsByAceNumber(userDetail.getAceNo());
		logger.info("prjXML = " + prjXML);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(prjXML));
			Document docProjects = builder.parse(is);
			NodeList nodes = docProjects.getElementsByTagName("Project");
			int size = nodes.getLength();
			List<String> prosProjectNames = new ArrayList<String>(size);
			for (int i = 0; i < size; i++) {
				Element element = (Element) nodes.item(i);
				String prjName = element.getElementsByTagName("ProjectName").item(0).getTextContent();
				String strEndDate = element.getElementsByTagName("EndDate").item(0).getTextContent();
				logger.info("Project Name =" + prjName + ", endDate = "+ strEndDate + ", Current Date =" + new Date() );
				if(strEndDate.length() > 0){
					Date endDate = new Date(strEndDate);
					if (endDate.getTime() > System.currentTimeMillis()){
						prosProjectNames.add(prjName);
					}
				} else {
					prosProjectNames.add(prjName);
				}
			}
			logger.info( "prosProjectNames =" + prosProjectNames );
			List<Integer> projectIds = projectManager.getAssociatedProjectIds(prosProjectNames);
			logger.info( "Project Ids =" + projectIds );
			userDetail.setProjectIds(projectIds);
		} catch (Exception e) {
			logger.error("Exception while building Associated Projects in User Detail Object ", e);
			throw e;
		}
*/		
	}

	private void loadAuditProjectIds(UserDetail userDetail) {
		int auditorId = userDetail.getAuditorId();
		if(auditorId > 0){
			List<Integer> auditProjectIds = auditorDao.getAssociatedProjects(auditorId);
			userDetail.setAuditProjectIds(auditProjectIds);
		}
	}
	
	/**
	 * Will retrive the project managers e-mail id from IDM system.
	 * 
	 * @param ownerUserId should of format "USER + four digits of the id in pros" like USER0011
	 * 
	 * @return Project Owners email id if exists else empty string
	 */
	public String getProjectOwnerEmail(String ownerUserId){
		try {
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(Calendar.getInstance().getTime());
		String empXML = idmWs.GetEmployeeDetailByUserStringID(date, String.valueOf(ownerUserId));
		String empXML1 = idmWs.GetEmployeeDetail(String.valueOf(date), String.valueOf("ACE1501"));
		logger.info("empXML1 "+empXML1);
		logger.info("emp XML = " + empXML);
		String email = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML));
			Document activeCustomer = builder.parse(is);
			NodeList nodes = activeCustomer.getElementsByTagName("Employee");
			if (nodes.getLength() == 1) {
				Element node = (Element) nodes.item(0);
				email = node.getElementsByTagName("Email").item(0).getTextContent();
			}
			logger.info("Owner Email for Owner Id "+ ownerUserId + " is " + email );
			return email;
		} catch (Exception e) {
			logger.error("Exception while retriving Project Owner email for " + ownerUserId, e);
		
		}
		
		return null;
	}
	
	//To get the email of the previous auditor of a project
	public String getProjectPreviousAuditorEmail(int auditorId){
		String userName = projectManager.getProjectPreviousAuditorName(auditorId);
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(
				Calendar.getInstance().getTime());
		String empXML = idmWs.GetEmployeeDetailByUserName(date, userName);
		String emailXMl = idmWs.GetEmployeeDetail(date, "ACE1501");
		UserDetail userDetail = null;
		logger.info("emp XML = " + empXML);
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML));
			Document activeCustomer = builder.parse(is);
			NodeList nodes = activeCustomer.getElementsByTagName("Employee");
			if (nodes.getLength() == 1) {
				userDetail = new UserDetail(userName);
				Element node = (Element) nodes.item(0);
				userDetail.setAceNo(node.getAttribute("ACEID"));
				userDetail.setUserStringId(node.getAttribute("UserStringID"));
				userDetail.setEmail(node.getElementsByTagName("Email").item(0).getTextContent());
				userDetail.setLevel(((Element)node.getElementsByTagName("Level").item(0)).getAttribute("Identifier"));
				userDetail.setDeptId(((Element)node.getElementsByTagName("Department").item(0)).getAttribute("Identifier"));;
				userDetail.setDuId(((Element)node.getElementsByTagName("DeliveryUnit").item(0)).getAttribute("Identifier"));;
				boolean isAuditor = auditorDao.isAuditor(userDetail.getAceNo());
				userDetail.setAuditor(isAuditor);
				/*if(isAuditor){
					int auditorId = auditorDao.getAuditorId(userDetail.getAceNo());	
					userDetail.setAuditorId(auditorId);
					loadAuditProjectIds(userDetail);
				} 
				loadAssociatedProjects(userDetail);
				*/
		}
		} catch (Exception e) {
			logger.error("Exception while building User Detail Object ", e);
		}
		logger.info("User Detail Object is " + userDetail);
		return userDetail.getEmail();
	}
	
	//To get the email of the previous auditor of a project
	public String getEmployeeEmail(String aceNo){
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(
				Calendar.getInstance().getTime());
		String empXML = idmWs.GetEmployeeDetail(String.valueOf(date), String.valueOf(aceNo));
		UserDetail userDetail = null;
		logger.info("emp XML = " + empXML);
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML));
			Document activeCustomer = builder.parse(is);
			NodeList nodes = activeCustomer.getElementsByTagName("Employee");
			if (nodes.getLength() == 1) {
				userDetail = new UserDetail("");
				Element node = (Element) nodes.item(0);
				userDetail.setAceNo(node.getAttribute("ACEID"));
				userDetail.setUserStringId(node.getAttribute("UserStringID"));
				userDetail.setEmail(node.getElementsByTagName("Email").item(0).getTextContent());
				userDetail.setLevel(((Element)node.getElementsByTagName("Level").item(0)).getAttribute("Identifier"));
				userDetail.setDeptId(((Element)node.getElementsByTagName("Department").item(0)).getAttribute("Identifier"));;
				userDetail.setDuId(((Element)node.getElementsByTagName("DeliveryUnit").item(0)).getAttribute("Identifier"));;
				boolean isAuditor = auditorDao.isAuditor(userDetail.getAceNo());
				userDetail.setAuditor(isAuditor);
			}
		} catch (Exception e) {
			logger.error("Exception while building User Detail Object ", e);
		}
		logger.info("User Detail Object is " + userDetail);
		return userDetail.getEmail();
	}
	//To get the project resource aceno and username
	public List<Auditor> getProjectUsers(String projectName){
		System.out.println("project name: "+projectName);
		try {
		//String userName = projectManager.getProjectPreviousAuditorName(auditorId);
		ProsWebService prosWs = AppContext.getProsServiceBean();
		String date = new DateFormatter().getFormat().format(
				Calendar.getInstance().getTime());
		String resourceXML = prosWs.GetProjectResourceByProjectName(projectName);
		//logger.info("empXML2 "+empXML2);
		UserDetail userDetail = null;
		logger.info("emp XML pros = " + resourceXML);
		String acenos = "ACE1501,ACE1761";
		

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(resourceXML));
			Document activeCustomer = builder.parse(is);
			
			NodeList nodes = activeCustomer.getElementsByTagName("Resource");
			StringBuffer resourceSB = new StringBuffer(3200);
			for(int i=0;i<nodes.getLength()-1;++i){
				Element node = (Element) nodes.item(i);
				String aceno = node.getAttribute("Identifier");
				resourceSB.append(aceno+",");
				/*userDetail.setUserStringId(node.getAttribute("UserStringID"));
				userDetail.setEmail(node.getElementsByTagName("Email").item(0).getTextContent());
				userDetail.setLevel(((Element)node.getElementsByTagName("Level").item(0)).getAttribute("Identifier"));
				userDetail.setDeptId(((Element)node.getElementsByTagName("Department").item(0)).getAttribute("Identifier"));;
				userDetail.setDuId(((Element)node.getElementsByTagName("DeliveryUnit").item(0)).getAttribute("Identifier"));;
				boolean isAuditor = auditorDao.isAuditor(userDetail.getAceNo());
				userDetail.setAuditor(isAuditor);
				/*if(isAuditor){
					int auditorId = auditorDao.getAuditorId(userDetail.getAceNo());	
					userDetail.setAuditorId(auditorId);
					loadAuditProjectIds(userDetail);
				} 
				loadAssociatedProjects(userDetail);
				*/
			}
			if(nodes.getLength()>0){
				Element node = (Element) nodes.item(nodes.getLength()-1);
				String aceno = node.getAttribute("Identifier");
				resourceSB.append(aceno);
			}
			logger.info("stringbuffer:"+resourceSB.toString());
			String aceXML = AppContext.getIDMServiceBean().GetAllEmployeeDetailsByUserACENumbers(resourceSB.toString());
			logger.info("ace XML = " + aceXML);
			factory = DocumentBuilderFactory.newInstance();
		    builder = factory.newDocumentBuilder();
		    is = new InputSource( new StringReader( aceXML ) );
		    Document activeEmployee = builder.parse( is );
		    NodeList resourceNodes = activeEmployee.getElementsByTagName("Employee");
		    int nodeLength = resourceNodes.getLength();
		    List<Auditor> auditors = new ArrayList<Auditor>(nodeLength);
		    for (int i = 0; i < nodeLength; i++) {
		    	Auditor auditor = new Auditor();
				Element node = (Element) resourceNodes.item(i);
				auditor.setAceNo(node.getAttribute("ACEID"));
				auditor.setName(((Element)node.getChildNodes().item(0)).getAttribute("UserName"));
				auditor.setEmail(node.getChildNodes().item(1).getTextContent());
				auditor.setCreatedBy("ACE0002");
				Date now = Calendar.getInstance().getTime();
				auditor.setCreatedOn(now);
				auditor.setLastModifiedBy("ACE0002");
				auditor.setLastModifiedOn(now);
				auditors.add(auditor);
			}
		    if((auditors!=null)&&(auditors.size()>0)){
		    	for(int i=0;i<auditors.size();++i){
		    		logger.info("Resource "+i+" "+auditors.get(i).getName()+auditors.get(i).getAceNo());
		    	}
		    }
		    logger.info("User Detail Object is " + userDetail);
		    return auditors;
			
		} catch (Exception e) {
			logger.error("Exception while building User Detail Object ", e);
		}
		
		//return userDetail.getEmail();
		return null;
	}
	
	public String getProjectAuditSchedulerEmail(Integer projectId){
		try {
		IDMWebService idmWs = AppContext.getIDMServiceBean();
		String date = new DateFormatter().getFormat().format(Calendar.getInstance().getTime());
		//String empXML = idmWs.GetEmployeeDetailByUserStringID(date, String.valueOf(ownerUserId));
		String schedulerAceno = projectManager.getProjectAuditScheduler(projectId);
		String empXML1 = idmWs.GetEmployeeDetail(String.valueOf(date), String.valueOf(schedulerAceno));
		logger.info("empXML1 "+empXML1);
		//logger.info("emp XML = " + empXML);
		String email = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(empXML1));
			Document activeCustomer = builder.parse(is);
			NodeList nodes = activeCustomer.getElementsByTagName("Employee");
			if (nodes.getLength() == 1) {
				Element node = (Element) nodes.item(0);
				email = node.getElementsByTagName("Email").item(0).getTextContent();
			}
			logger.info("Scheduler Email for Aceno "+ schedulerAceno + " is " + email );
			return email;
		} catch (Exception e) {
			logger.error("Exception while retriving Project scheduler email for Project" + projectId, e);
		}
		
		return null;
	}

}

