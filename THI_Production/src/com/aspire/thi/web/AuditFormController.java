package com.aspire.thi.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.common.ResourceUtility;
import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.ProjectManager;
import com.aspire.thi.service.ThiManager;
import com.aspire.thi.service.AuthenticationManager;

@SuppressWarnings("deprecation")
public class AuditFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private ThiManager thiManager;
	private ProjectManager projectManager;
	private AuthenticationManager authenticationManager;

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	public void setThiManager(ThiManager thiManager) {
		this.thiManager = thiManager;
	}

	public ThiManager getThiManager() {
		return thiManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
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
		if (userDetail != null) {
			return super.handleRequest(request, response);
		} else {
			return new ModelAndView(new RedirectView("login.htm"));
		}

	}

	@Override
	protected java.lang.Object formBackingObject(HttpServletRequest request) throws Exception {
		String projectId = request.getParameter("projectId");
		ThiScore score = new ThiScore();
		if (StringUtils.isBlank(projectId) == Boolean.FALSE && StringUtils.isNumeric(projectId)) {
			score.setProjectId(Integer.valueOf(projectId));
		}
		String auditCycle = request.getParameter("auditCycle");
		try {
			if (StringUtils.isBlank(auditCycle) == Boolean.FALSE) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
				Date date = dateFormat.parse(auditCycle);
				score.setAuditCycleDate(date);
			}
		} catch (Exception e) {
			// Ignore Error
		}
		return score;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request, Object command, Errors errors)
			throws Exception {
		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Project> projects = new ArrayList<Project>();
		if (userDetail != null) {
			Properties aceNoProps = ResourceUtility.loadPropertiesFromWebPath("/WEB-INF/qa.properties");
			if (aceNoProps.containsKey(userDetail.getAceNo()) == Boolean.TRUE) {
				projects.addAll(projectManager.getAllProjects());
			} else {

				if (userDetail.isAuditor() == Boolean.TRUE && userDetail.getAuditProjectIds() != null
						&& userDetail.getAuditProjectIds().size() > 0) {
					projects.addAll(projectManager.getProjects(userDetail.getAuditProjectIds()));
				}
				if (userDetail.getProjectIds() != null && userDetail.getProjectIds().size() > 0) {
					projects.addAll(projectManager.getProjects(userDetail.getProjectIds()));
				}
			}
		}
		model.put("projects", projects);
		model.put("assesmentTypes", thiManager.getAssesmentTypes());
		model.put("userDetail", userDetail);
		return model;
	}

	@SuppressWarnings("unused")
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		ThiScore thiForm = (ThiScore) command;
		return null;
	}

}