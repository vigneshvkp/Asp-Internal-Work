package com.aspire.thi.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.aspire.thi.domain.Department;
import com.aspire.thi.domain.Project;
import com.aspire.thi.service.DepartmentManager;
import com.aspire.thi.service.ProjectComparator;
import com.aspire.thi.service.ProjectManager;

public class ProjectController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ProjectManager projectManager;
	private DepartmentManager departmentManager;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
        String now = (new java.util.Date()).toString();
        
        logger.info("Returning project view with " + now);
        
        Map<String, Object> modal = new HashMap<String, Object>();
        modal.put("now", now);
        List<Project> projs;
        List<Department> depts;
        
        String prosProjectStr = request.getParameter("prosProject");
        String thiNameStr = request.getParameter("thiName");
        String paginationStr = request.getParameter("pagination");
        String sortByName = request.getParameter("sortByName");
        String sortDirection = request.getParameter("sortDirection");
        String deptStr = request.getParameter("dept");
        boolean showPrev = false;
        boolean showNext = false;
        String prosProjSortDir = "asc";
        String thiNameSortDir = "asc";
        int pagination;
        
        Map<String, String> searchKeys = new HashMap<String, String>();
        
        searchKeys.put("prosProjectStr", prosProjectStr);
        searchKeys.put("thiNameStr", thiNameStr);
        searchKeys.put("deptStr", deptStr);
        
        depts = departmentManager.getDelivaryUnits();
        deptStr = (deptStr == null) ? "0" : deptStr;
        if((prosProjectStr != null && prosProjectStr.trim().length() > 0) || (thiNameStr != null && thiNameStr.trim().length() > 0) || (Integer.valueOf(deptStr) > 0)) {
//        	projs = this.projectManager.searchProjects(prosProjectStr, thiNameStr);
        	projs = this.projectManager.searchProjects(searchKeys);
        } else {
        	projs = this.projectManager.getProjectList();
        }
        
        if(prosProjectStr != null && prosProjectStr.trim().length() > 0) {
        	modal.put("prosProjectStr", prosProjectStr); 
        } else {
        	modal.put("prosProjectStr", "");
        }
        
        if(thiNameStr != null && thiNameStr.trim().length() > 0) {
        	modal.put("thiNameStr", thiNameStr); 
        } else {
        	modal.put("thiNameStr", "");
        }
        
        if (paginationStr != null && paginationStr.trim().length() >0) {
        	pagination = Integer.valueOf(paginationStr);
        } else {
        	pagination = 1;
        }
        //sorting
        
        ProjectComparator comparator = ProjectComparator.getInstance();
        comparator.setSortDirection(sortDirection);
        
        if(sortByName != null && sortByName.trim().length() > 0) {
        	modal.put("sortByName", sortByName);
        	Collections.sort(projs, comparator);
        	if(sortByName.equals("ProsProject")) {
        		thiNameSortDir = "asc";
        		if(sortDirection.equals("asc")) {
            		prosProjSortDir = "desc";
        		} else {
        			prosProjSortDir = "asc";
        		}
        	} else {
        		prosProjSortDir = "asc";
        		if(sortDirection.equals("asc")) {
        			thiNameSortDir = "desc";
        		} else {
        			thiNameSortDir = "asc";
        		}        		
        	}
        } else {
        	modal.put("sortByName", "");
        }
        
        int fromIndex = (pagination - 1) * 20;
        int toIndex = ((pagination * 20) > projs.size()) ? projs.size() : (pagination * 20);
        //This is a very costly operation to get ProsProjectName, we will re-look at this in next version[
//        List<ProjectHelper>  projects = new ArrayList<ProjectHelper>();
//        for (Project proj : projs.subList(fromIndex,  toIndex)) {
//        	ProjectHelper project = ProjectHelper.convertToAddProject(proj);
//        	project.setProsProjectName(
//        			this.projectManager.getProsProjectById(proj.getProsProjectId()).getProjectName());
//			projects.add(project);
//		}
        //]
        if (fromIndex <= 0) {
        	showPrev = true;
        }
        if (toIndex >= projs.size()) {
        	showNext = true;
        }
        modal.put("depts", depts);
        modal.put("deptStr", deptStr);
        modal.put("prosProjSortDir", prosProjSortDir);
        modal.put("thiNameSortDir", thiNameSortDir);
        modal.put("showPrev", showPrev);
        modal.put("showNext", showNext);
        modal.put("pagination", pagination);
        modal.put("projects", ((fromIndex < toIndex) ? projs.subList(fromIndex,  toIndex) : projs));

        return new ModelAndView("listprojects", "modal", modal);
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
}
