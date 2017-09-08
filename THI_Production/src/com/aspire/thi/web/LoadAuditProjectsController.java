package com.aspire.thi.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.domain.Project;
import com.aspire.thi.domain.ProjectAudit;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.ProjectManager;

public class LoadAuditProjectsController implements Controller {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		ModelAndView model = null;
		if (userDetail != null) {
			model = new ModelAndView("auditHome");
			List<ProjectAudit> auditProject = new ArrayList<ProjectAudit>();
			if (userDetail.getAuditProjectIds() != null && userDetail.getAuditProjectIds().isEmpty() == Boolean.FALSE) {
				auditProject = projectManager.getProjectsWithAuditDate(userDetail.getAuditProjectIds());
				model.addObject("auditProjects", auditProject);
			}
			Calendar cal = Calendar.getInstance();
			String auditCycle = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.YEAR);
			model.addObject("auditCycle", auditCycle);
			model.addObject("userDetail", userDetail);

		} else {
			model = new ModelAndView(new RedirectView("login.htm"));
		}
		return model;
	}

}
