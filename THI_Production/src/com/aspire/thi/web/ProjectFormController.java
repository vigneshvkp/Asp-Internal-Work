package com.aspire.thi.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.aspire.thi.common.THIContextLoaderListener;
import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.AuditorManager;
import com.aspire.thi.service.AuthenticationManager;
import com.aspire.thi.service.ProjectAuditorHelper;
import com.aspire.thi.service.ProjectManager;
import com.aspire.thi.utils.EmailManager;

@SuppressWarnings("deprecation")
public class ProjectFormController extends SimpleFormController{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ProjectManager projectManager;
	private AuditorManager auditorManager;
	private AuthenticationManager authenticationManager;
	
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		/*
		 * Modified to send email notification to
		 * i. Previous Auditor
		 * ii. Project Resources
		 * iii.other users
		 * iv. THI coordinator(who assigns the audit)
		 * previously email notification is sent to auditor,QA Lead - Krishna Sivaramakrishnan 
		 * and the project owner
		 * notification also includes the auditdate
		 */
		Project project = new Project();
		ProjectAuditor projectAuditor = new ProjectAuditor();
		String idAsString = request.getParameter("id");
		UserDetail userDetail = (UserDetail)request.getSession().getAttribute("UserDetail");

		if (idAsString != null && idAsString.trim().length() > 0) {
			project.setId(Integer.parseInt(idAsString));
			projectAuditor.setId(Integer.parseInt(idAsString));
		} else {
			project.setId(0);
			projectAuditor.setId(0);
		}

		projectAuditor.setProjectId(Integer.valueOf(request.getParameter("projectId")));
		projectAuditor.setAuditorId(Integer.valueOf(request.getParameter("auditorId")));
		projectAuditor.setAuditComplete(false);
		String auditDateStr = request.getParameter("auditdate");
		String[] auditDt = auditDateStr.split("/");
		projectAuditor.setAuditDate(Date.valueOf(auditDt[2] + "-" + auditDt[1] + "-" + auditDt[0]));
		projectAuditor.setCreatedBy(userDetail.getAceNo());
		projectAuditor.setCreatedOn(Calendar.getInstance().getTime());
		projectAuditor.setLastModifiedBy(userDetail.getAceNo());
		projectAuditor.setLastModifiedOn(Calendar.getInstance().getTime());

		project.setLastModifiedBy(userDetail.getAceNo());
		project.setLastModifiedOn(Calendar.getInstance().getTime());
		project.setAuditFrequency(Integer.parseInt(request.getParameter("auditFrequency")));

		projectManager.saveProject(project);
		Project projectDetails = projectManager.getProjectByID(Integer.valueOf(request.getParameter("projectId")));
		System.out.println("Project Details name:"+projectDetails.getProjectName());
		List<Auditor> projectResources = authenticationManager.getProjectUsers(projectDetails.getProjectName());
		request.setAttribute("emps", projectResources);
		
		boolean isRowUpdated = this.auditorManager.saveProjectAuditor(projectAuditor);
		request.setAttribute("isRowUpdated", isRowUpdated);
		
		//Update UserDetail object to have auditor
		if (userDetail.isAuditor() && isRowUpdated) {
			if ( userDetail.getAuditorId() == projectAuditor.getAuditorId() ){
				userDetail.getAuditProjectIds().add(projectAuditor.getProjectId());
			}
		}
		String[] notifyToAceNosArray = null; 
		String otherAceNos = request.getParameter("otheracenos");
		System.out.println("other acenos: "+otherAceNos);
		if((otherAceNos!=null)&&(!otherAceNos.trim().equals(""))){
			if(otherAceNos.contains("-")){
				notifyToAceNosArray = otherAceNos.split("-");
			}else{
				notifyToAceNosArray = new String[1];
				notifyToAceNosArray[0] = otherAceNos;
			}
			
			
			StringBuffer sb1 = new StringBuffer(6200);
			if(notifyToAceNosArray!=null && notifyToAceNosArray.length>0){
				int i;
				for(i=0;i<notifyToAceNosArray.length-1;++i){
					sb1.append(notifyToAceNosArray[i]+",");
				}
				sb1.append(notifyToAceNosArray[i]);
			}
			System.out.println("otherusers: "+sb1.toString());
		}
		
		//Retrieve the other emails
		String otherEmailStr = request.getParameter("otheremails");
		String[] otherEmails = null;
		if((otherEmailStr!=null)&&(!otherEmailStr.trim().equals(""))){
			otherEmailStr = otherEmailStr.trim();
			if(otherEmailStr.contains(",")){
				otherEmails = otherEmailStr.split(",");
			}else{
				otherEmails = new String[1];
				otherEmails[0] = otherEmailStr;
			}
		}
		
		HttpSession session = request.getSession();
		UserDetail ud = (UserDetail)session.getAttribute("UserDetail");
		String thiCoordinatorEmail = ud.getEmail(); 
		if(isRowUpdated) {
			sendProjectAllocationMail(projectAuditor.getAuditorId(), projectAuditor.getProjectId(), notifyToAceNosArray, otherEmails,thiCoordinatorEmail, auditDateStr);
			return showForm(request, response, errors);			
		} else {
			Map<String, Object> modal = new HashMap<String, Object>();
			List<Project> projects = projectManager.getProjectList();
			List<Auditor> auditors = auditorManager.getAuditorList();	
			modal.put("projects", projects);
			modal.put("auditors", auditors);
			modal.put("emps", projectResources);
			ProjectAuditorHelper projAuditor = ProjectAuditorHelper.convertProjectAuditor(projectAuditor);
			modal.put("projectauditor", projAuditor);
			modal.put("isRowUpdated", isRowUpdated);
			return showForm(request, response, errors);
		}
	}

	private void sendProjectAllocationMail(int auditorId, int projectId) {
		//Now Allocation mails will only be sent only to Auditor, QA Person & Project Owner
		List<String> ccAddress = new ArrayList<String>(1);
		ccAddress.add(THIContextLoaderListener.getQALead());
		Auditor auditor = auditorManager.getAuditorByID(auditorId);
		Project project = projectManager.getProjectByID(projectId);
		
		// Populating Owner email
		String ownerUserId = project.getOwnerUserId();
		if(ownerUserId != null && ownerUserId.trim().length() > 0){
			String ownerEmail = authenticationManager.getProjectOwnerEmail(ownerUserId);
			if(ownerEmail.trim().length() > 0 ){
				ccAddress.add(ownerEmail);
			}
		}
		
		String toAddress = auditor.getEmail();
		String subject = "Mail From Project Health System"; 
		StringBuilder body = new StringBuilder(1000);
		body.append("Dear ");
		body.append(auditor.getName());
		body.append(", <br/><br/>");
		body.append("You have been associated to do this cycle THI for ");
		body.append(project.getProjectName());
		body.append(". Please complete the audit before schedule.\n<br/><br/>");
		body.append("Regards,<br/>");
		body.append("QA Team");
		logger.info( "THI Allocation for " + project.getProjectName()  + " mail sent to " + toAddress + ", and CCed to " + ccAddress );
		int status = EmailManager.getInstance().sendMail(toAddress, subject, body.toString(), ccAddress, null, null, null);
		logger.info ("THI Allocation Mail sent status is " + status);
		
	}
	/*
	 * Email is sent to 
	 * i. Auditor
	 * ii. Project owner
	 * iii. QA Lead
	 * iv. Select project resources
	 * v. Other emails
	 * vi. THI coordinator
	 */
	private void sendProjectAllocationMail(int auditorId, int projectId, String[] notifyAceNos,String[] otherEmails, String thiCoordinatorEmail, String auditDateStr) {
		List<String> ccAddress = new ArrayList<String>();
		ccAddress.add(THIContextLoaderListener.getQALead());
		Auditor auditor = auditorManager.getAuditorByID(auditorId);
		Project project = projectManager.getProjectByID(projectId);
		
		boolean addEmailFlag = true;
		String toAddress1 = auditor.getEmail();
		// Populating Owner email
		String ownerUserId = project.getOwnerUserId();
		if(ownerUserId != null && ownerUserId.trim().length() > 0){
			String ownerEmail = authenticationManager.getProjectOwnerEmail(ownerUserId);
			if((ownerEmail != null)&&(ownerEmail.trim().length() > 0 )){
				if(ownerEmail.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
					addEmailFlag = false;
				}else{
					for(String mail:ccAddress){
						if(mail.trim().toLowerCase().equals(ownerEmail.trim().toLowerCase())){
							addEmailFlag = false;
						}
					}
				}
				if(addEmailFlag){
					ccAddress.add(ownerEmail);
				}
			}
		}
		
		// Populating other users
		if(notifyAceNos!=null && notifyAceNos.length>0){
			int i;
			for(i=0;i<notifyAceNos.length;++i){
				System.out.println("get employee email otheruserids"+notifyAceNos[i]);
				if(!notifyAceNos[i].trim().equals("")){
					addEmailFlag = true;
					String email = authenticationManager.getEmployeeEmail(notifyAceNos[i]);
					if(email!=null && email.trim().length()>0){
						if(email.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
							addEmailFlag = false;
						}else{
							for(String mail:ccAddress){
								if(mail.trim().toLowerCase().equals(email.trim().toLowerCase())){
									addEmailFlag = false;
								}
							}
						}
						if(addEmailFlag){
							ccAddress.add(email);
						}
					}
				}
			}
		}
		
		// Populate other emails
		if(otherEmails!=null && otherEmails.length>0){
			int i;
			for(i=0;i<otherEmails.length;++i){
				if(!otherEmails[i].trim().equals("")){
					addEmailFlag = true;
					if(otherEmails[i].trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
						addEmailFlag = false;
					}else{
						for(String mail:ccAddress){
							if(mail.trim().toLowerCase().equals(otherEmails[i].trim().toLowerCase())){
								addEmailFlag = false;
							}
						}
					}
					if(addEmailFlag){
						ccAddress.add(otherEmails[i]);
					}
				}
			}
		}
		//add thi coordinator
		addEmailFlag = true;
		if(thiCoordinatorEmail.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
			addEmailFlag = false;
		}else{
			for(String mail:ccAddress){
				if(mail.trim().toLowerCase().equals(thiCoordinatorEmail.trim().toLowerCase())){
					addEmailFlag = false;
				}
			}
		}
		if(addEmailFlag){
			ccAddress.add(thiCoordinatorEmail);
		}
		String toAddress = auditor.getEmail();
		String subject = "Mail From Project Health System"; 
		StringBuilder body = new StringBuilder(1000);
		body.append("Dear ");
		body.append(auditor.getName());
		body.append(", <br/><br/>");
		body.append("You have been associated to do this cycle THI for ");
		logger.info("You have been associated to do this cycle THI for ");
		body.append(project.getProjectName());
		logger.info(project.getProjectName());
		body.append(". The audit date is "+auditDateStr+". Please complete the audit before schedule.\n<br/><br/>");
		logger.info(". The audit date is "+auditDateStr+". Please complete the audit before schedule.\n<br/><br/>");
		body.append("Regards,<br/>");
		logger.info("Regards,<br/>");
		body.append("QA Team");
		logger.info("QA Team");
		logger.info( "THI Allocation for " + project.getProjectName()  + " mail sent to " + toAddress + ", and CCed to " + ccAddress );
		int status = EmailManager.getInstance().sendMail(toAddress, subject, body.toString(), ccAddress, null, null, null);
		logger.info ("THI Allocation Mail sent status is " + status);
		
	}
	
	/*
	 * Email is sent to 
	 * i. Auditor
	 * ii. Project owner
	 * iii. QA Lead
	 * iv. Select project resources
	 * v. Other emails
	 * vi. THI coordinator
	 * vii. Previous Auditor
	 */
	private void sendProjectAllocationMail(int auditorId,int prevAuditorId, int projectId, String[] notifyAceNos, String[] otherEmails, String thiCoordinatorEmail, String auditDateStr) {
		List<String> ccAddress = new ArrayList<String>();
		ccAddress.add(THIContextLoaderListener.getQALead());
		Auditor auditor = auditorManager.getAuditorByID(auditorId);
		Project project = projectManager.getProjectByID(projectId);
		
		boolean addEmailFlag = true;
		String toAddress1 = auditor.getEmail();
		// Populating Owner email
		String ownerUserId = project.getOwnerUserId();
		if(ownerUserId != null && ownerUserId.trim().length() > 0){
			String ownerEmail = authenticationManager.getProjectOwnerEmail(ownerUserId);
			if((ownerEmail != null)&&(ownerEmail.trim().length() > 0 )){
				if(ownerEmail.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
					addEmailFlag = false;
				}else{
					for(String mail:ccAddress){
						if(mail.trim().toLowerCase().equals(ownerEmail.trim().toLowerCase())){
							addEmailFlag = false;
						}
					}
				}
				if(addEmailFlag){
					ccAddress.add(ownerEmail);
				}
			}
		}
		
		//Populating the previous auditor email
		addEmailFlag = true;
		String prevAuditorEmail = authenticationManager.getProjectPreviousAuditorEmail(prevAuditorId);
		if((prevAuditorEmail != null)&&(prevAuditorEmail.trim().length() > 0 )){
			if(prevAuditorEmail.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
				addEmailFlag = false;
			}else{
				for(String mail:ccAddress){
					if(mail.trim().toLowerCase().equals(prevAuditorEmail.trim().toLowerCase())){
						addEmailFlag = false;
					}
				}
			}
			if(addEmailFlag){
				ccAddress.add(prevAuditorEmail);
			}
		}
		
		// Populating other users
		if(notifyAceNos!=null && notifyAceNos.length>0){
			int i;
			for(i=0;i<notifyAceNos.length;++i){
				if(!notifyAceNos[i].trim().equals("")){
					addEmailFlag = true;
					String email = authenticationManager.getEmployeeEmail(notifyAceNos[i]);
					if(email!=null && email.trim().length()>0){
						if(email.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
							addEmailFlag = false;
						}else{
							for(String mail:ccAddress){
								if(mail.trim().toLowerCase().equals(email.trim().toLowerCase())){
									addEmailFlag = false;
								}
							}
						}
						if(addEmailFlag){
							ccAddress.add(email);
						}
					}
				}
			}
		}
		
		// Populate other emails
		if(otherEmails!=null && otherEmails.length>0){
			int i;
			for(i=0;i<otherEmails.length;++i){
				addEmailFlag = true;
				if(!otherEmails[i].trim().equals("")){
					if(otherEmails[i].trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
						addEmailFlag = false;
					}else{
						for(String mail:ccAddress){
							if(mail.trim().toLowerCase().equals(otherEmails[i].trim().toLowerCase())){
								addEmailFlag = false;
							}
						}
					}
					if(addEmailFlag){
						ccAddress.add(otherEmails[i]);
					}
				}
			}
		}
		//Add THI Coordinator Email
		addEmailFlag = true;
		if(thiCoordinatorEmail.trim().toLowerCase().equals(toAddress1.trim().toLowerCase())){
			addEmailFlag = false;
		}else{
			for(String mail:ccAddress){
				if(mail.trim().toLowerCase().equals(thiCoordinatorEmail.trim().toLowerCase())){
					addEmailFlag = false;
				}
			}
		}
		if(addEmailFlag){
			ccAddress.add(thiCoordinatorEmail);
		}
		String toAddress = auditor.getEmail();
		String subject = "Mail From Project Health System"; 
		StringBuilder body = new StringBuilder(1000);
		body.append("Dear ");
		body.append(auditor.getName());
		body.append(", <br/><br/>");
		body.append("You have been associated to do this cycle THI for ");
		body.append(project.getProjectName());
		body.append(". The audit date is "+auditDateStr+". Please complete the audit before schedule.\n<br/><br/>");
		body.append("Regards,<br/>");
		body.append("QA Team");
		logger.info( "THI Allocation for " + project.getProjectName()  + " mail sent to " + toAddress + ", and CCed to " + ccAddress );
		int status = EmailManager.getInstance().sendMail(toAddress, subject, body.toString(), ccAddress, null, null, null);
		logger.info ("THI Allocation Mail sent status is " + status);
		
	}
	
	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
			throws Exception {
		String idAsString = request.getParameter("id");
		Map<String, Object> modal = new HashMap<String, Object>();
		List<Auditor> auditors = auditorManager.getAuditorList();
		modal.put("auditors", auditors);
		modal.put("isRowUpdated", request.getAttribute("isRowUpdated"));
		if(idAsString != null && idAsString != "") {
			Project project = this.projectManager.getProjectByID(Integer.valueOf(idAsString));
			ProjectAuditor projectAuditorMap = auditorManager.getProjectAuditorByID(Integer.valueOf(idAsString));
			modal.put("projectAuditorHelpers", auditorManager.getProjectAuditorsByProjectId(idAsString));
			if(projectAuditorMap != null){
				modal.put("projectauditor", projectAuditorMap);
				modal.put("notifyusers", projectAuditorMap);
			}
			modal.put("project", project);
			if(request.getAttribute("emps") == null){
				List<Auditor> projectResources = authenticationManager.getProjectUsers(project.getProjectName());
				modal.put("emps", projectResources);
			}else{
				modal.put("emps", request.getAttribute("emps"));
			}
			return new ModelAndView("addproject", "modal", modal);
		} else {
			Project proj = new Project();
			proj.setId(0);
			proj.setActive(true);
			modal.put("project", proj);
			ProjectAuditorHelper projAuditor = new ProjectAuditorHelper();
			projAuditor.setAuditComplete(false);
			projAuditor.setProjectId(Integer.valueOf(0));
			modal.put("projectauditor", projAuditor);
			return new ModelAndView("addproject", "modal", modal);
		}
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public AuditorManager getAuditorManager() {
		return auditorManager;
	}

	public void setAuditorManager(AuditorManager auditorManager) {
		this.auditorManager = auditorManager;
	}
	
	public void setAuthenticationManager(AuthenticationManager authenticationManager ) {
		this.authenticationManager = authenticationManager ;
	}
	
	public AuthenticationManager  getAuthenticationManager() {
		return authenticationManager;
	}
}
