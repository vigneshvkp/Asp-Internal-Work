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
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.ProjectManager;

public class LoadAssociatedProjectsController implements Controller {

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
			model = new ModelAndView("projectHome");
			List<Project> projects = new ArrayList<Project>();
			if (userDetail.getProjectIds() != null && userDetail.getProjectIds().isEmpty() == Boolean.FALSE) {
				projects = projectManager.getProjects(userDetail.getProjectIds());
				model.addObject("projects", projects);
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
