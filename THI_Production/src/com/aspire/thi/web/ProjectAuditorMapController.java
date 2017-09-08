package com.aspire.thi.web;

/*import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.common.THIContextLoaderListener;
import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.ProsProject;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.AuditorManager;
import com.aspire.thi.service.AuthenticationManager;
import com.aspire.thi.service.ProjectAuditorHelper;
import com.aspire.thi.service.ProjectManager;
import com.aspire.thi.utils.EmailManager;*/
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("deprecation")
public class ProjectAuditorMapController extends SimpleFormController{/*

	protected final Log logger = LogFactory.getLog(getClass());
	private ProjectManager projectManager;
	private AuthenticationManager authenticationManager;
	private AuditorManager auditorManager;
	
	private boolean isSaved=false;
	
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		ProjectAuditor projectAuditor = new ProjectAuditor();
		String idAsString = request.getParameter("id");
		UserDetail userDetail = (UserDetail)request.getSession().getAttribute("UserDetail");
		
		if (idAsString != null && idAsString != "") {
			projectAuditor.setId(Integer.parseInt(idAsString));
		} else {
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
		
		boolean isRowUpdated= this.auditorManager.saveProjectAuditor(projectAuditor);
		
		//Update UserDetail object to have auditor
		if (userDetail.isAuditor() && isRowUpdated) {
			if ( userDetail.getAuditorId() == projectAuditor.getAuditorId() ){
				userDetail.getAuditProjectIds().add(projectAuditor.getProjectId());
			}
		}
		isSaved=true;
		if(isRowUpdated) {
			sendProjectAllocationMail(projectAuditor.getAuditorId(), projectAuditor.getProjectId());
			return showForm(request, response, errors);			
		} else {
			Map<String, Object> modal = new HashMap<String, Object>();
			List<Project> projects = projectManager.getProjectList();
			List<Auditor> auditors = auditorManager.getAuditorList();			
			modal.put("projects", projects);
			modal.put("auditors", auditors);
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
		ProsProject prosProject = projectManager.getProsProjectById(project.getProsProjectId());
		String ownerUserId = prosProject.getOwnerUserId();
		if(ownerUserId != null && ownerUserId.trim().length() > 0){
			String ownerEmail = authenticationManager.getProjectOwnerEmail(ownerUserId);
			if(ownerEmail.trim().length() > 0 ){
				ccAddress.add(ownerEmail);
			}
		}
		
		String toAddress = auditor.getEmail();
		String subject = "Mail From Project Health System"; 
//		String subject = "Mail From Project Health System( THI Test Environment )"; 
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

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
			throws Exception {
		String idAsString = request.getParameter("id");
		
		Map<String, Object> modal = new HashMap<String, Object>();
		
		List<Auditor> auditors = auditorManager.getAuditorList();
		ProjectAuditor projectAuditorMap;
		modal.put("auditors", auditors);
		modal.put("isRowUpdated", true);
		modal.put("isSaved", isSaved);
		
		if(idAsString != null && idAsString.trim().length() > 0) {
			projectAuditorMap=auditorManager.getProjectAuditorByID(Integer.valueOf(idAsString));
			if(projectAuditorMap!=null){
				modal.put("projectauditor", projectAuditorMap);
			}
			return new ModelAndView("projectauditormap", "modal", modal);
			
		} else {
			ProjectAuditorHelper projAuditor = new ProjectAuditorHelper();
			projAuditor.setAuditComplete(false);
			projAuditor.setProjectId(Integer.valueOf(0));
			modal.put("projectauditor", projAuditor);
			return new ModelAndView("projectauditormap", "modal", modal);
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
*/}
