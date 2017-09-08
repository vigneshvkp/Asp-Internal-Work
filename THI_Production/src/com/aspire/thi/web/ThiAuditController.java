package com.aspire.thi.web;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.Auditor;
import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.ThiManager;
import com.aspire.thi.service.ProjectManager;
import com.aspire.thi.service.AuthenticationManager;

public class ThiAuditController implements Controller {

	private static final String PARAM_ASSESMENT_TYPE = "assesmentType";

	private static final String PARAM_AUDIT_CYCLE = "auditCycle";

	private static final String PARAM_PROJECT_ID = "projectId";

	private static final String AJAX_LOGIN = "ajaxLogin";

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private ThiManager thiManager;
	private ProjectManager projectManager;
	private AuthenticationManager authenticationManager;
	public void setThiManager(ThiManager thiManager) {
		this.thiManager = thiManager;
	}

	public ThiManager getThiManager() {
		return thiManager;
	}
	

	/**
	 * @return the projectManager
	 */
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	/**
	 * @param projectManager the projectManager to set
	 */
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		ModelAndView model = null;
		if (userDetail == null) {
			model = new ModelAndView(AJAX_LOGIN);
		} else if (userDetail != null && userDetail.isAuditor()) {
			model = new ModelAndView("thiAudit");
			String projectIdStr = request.getParameter(PARAM_PROJECT_ID);
			String auditDate = request.getParameter(PARAM_AUDIT_CYCLE);
			ThiScore thiAudit = null;
			if (StringUtils.isBlank(projectIdStr) == Boolean.FALSE && StringUtils.isNumeric(projectIdStr)
					&& StringUtils.isBlank(auditDate) == Boolean.FALSE && auditDate.split("/").length == 2) {
				Integer projectId = Integer.valueOf(projectIdStr);
				Calendar cal = Calendar.getInstance();
				String[] auditCycle = auditDate.split("/");
				cal.set(Calendar.MONTH, Integer.valueOf(auditCycle[0]) - 1);
				cal.set(Calendar.YEAR, Integer.valueOf(auditCycle[1]));

				if (userDetail.getAuditProjectIds().contains(projectId)) {
					thiAudit = this.thiManager.getThiScore(projectId, cal.getTime());
					if (thiAudit == null) {
						String auditType = request.getParameter(PARAM_ASSESMENT_TYPE);
						if (StringUtils.isBlank(auditType) == Boolean.FALSE && StringUtils.isNumeric(auditType)) {
							thiAudit = thiManager.createNewAudit(Integer.valueOf(auditType));
							thiAudit.setAuditCycleDate(cal.getTime());
							thiAudit.setProjectId(projectId);
						} else {
							thiAudit = new ThiScore();
							thiAudit.setProjectId(projectId);
							thiAudit.setAuditCycleDate(cal.getTime());
							model.addObject("assesmentTypes", thiManager.getAssesmentTypes());
							model.addObject("selectAssesmentType", Boolean.TRUE);
						}
					} else {
						thiAudit.setAuditorId(userDetail.getAuditorId());
						ProjectAuditor projectAuditor = thiManager.getProjectAuditor(projectId, cal.getTime());
						thiAudit.setAuditComplete(projectAuditor.getAuditComplete());
					}
					model.addObject("thiAudit", thiAudit);
					Project project = projectManager.getProjectByID(projectId);
					System.out.println("Project Name:"+project.getProjectName());
					List<String> auditeeNames = this.thiManager.getProjectAuditee(projectId);
					List<Auditor> projectAuditee = new java.util.ArrayList<Auditor>();
					List<Auditor> projectResourceList = authenticationManager.getProjectUsers(project.getProjectName());
					if((projectResourceList!=null)&&(auditeeNames!=null)){
						for(int i=0;i<auditeeNames.size();++i){
							for(int k=0;k<projectResourceList.size();++k){
								Auditor auditor = projectResourceList.get(k);
								if(auditor.getName().equals(auditeeNames.get(i))){
									projectAuditee.add(auditor);
									projectResourceList.remove(k);
									break;
								}
							}
						}
					}
					model.addObject("projectUsers",projectResourceList);
					model.addObject("projectAuditees",projectAuditee);
					
				}
			}
		} else {
			model = new ModelAndView(new RedirectView("loadAuditProjects.htm"));
		}
		model.addObject("userDetail", userDetail);
		return model;
	}

}