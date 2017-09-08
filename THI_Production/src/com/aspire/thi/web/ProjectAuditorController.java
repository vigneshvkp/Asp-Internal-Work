package com.aspire.thi.web;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.service.AuditorManager;
import com.aspire.thi.service.ProjectAuditorComparator;
import com.aspire.thi.service.ProjectAuditorHelper;
import com.aspire.thi.service.ProjectManager;

@SuppressWarnings("deprecation")
public class ProjectAuditorController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ProjectManager projectManager;
	private AuditorManager auditorManager;

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

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		String thiName = request.getParameter("thiName"); 
		String auditCompleteStr = request.getParameter("auditComplete");
        String paginationStr = request.getParameter("pagination");
        String sortByName = request.getParameter("sortByName");
        String sortDirection = request.getParameter("sortDirection");
        boolean showPrev = false;
        boolean showNext = false;
        int pagination;
		boolean auditComplete = false;
		Date startDate = null;
		Date endDate = null;
        String auditorSortDir = "asc";
        String thiNameSortDir = "asc";
		if(startDateStr != null && startDateStr.trim().length() > 0) {
			String[] startDt = startDateStr.split("/");
			startDate = Date.valueOf(startDt[2] + "-" + startDt[1] + "-" + startDt[0]);
		}
		if(endDateStr != null && endDateStr.trim().length() > 0) {
			String[] endDt = endDateStr.split("/");
			endDate = Date.valueOf(endDt[2] + "-" + endDt[1] + "-" + endDt[0]);
		}
		if(auditCompleteStr != null && auditCompleteStr.equals("on")) {
			auditComplete = true;
		}
		Map<String, Object> modal = new HashMap<String, Object>();
		List<Auditor> auditors = this.auditorManager.getAuditorList();
		List<ProjectAuditorHelper> projectAuditors;

		if (paginationStr != null && paginationStr.trim().length() >0) {
        	pagination = Integer.valueOf(paginationStr);
        } else {
        	pagination = 1;
        }
		
		if(startDate != null || endDate != null || (thiName != null && thiName.trim().length() > 0)) {
			projectAuditors = auditorManager.searchProjectAuditors(thiName, startDate, endDate, auditComplete);
		} else {
			/*/****************************************************************************************************
			Set start and end dates to be 2 months gap based on current date.
			If Current date is Jan 10, 2010 then 
					start date should be Jan 01 2010 and 
					end date should be Feb 28 2010
			   Current date is Feb 10, 2010 then also
					start date should be Jan 01 2010 and 
					end date should be Feb 28 2010
			   Current date is Apr 05, 2010 then 
					start date should be Mar 01 2010 and 
					end date should be Apr 30 2010
			   Current date is Oct 21, 2010 then 
					start date should be Sep 01 2010 and 
					end date should be Oct 30 2010
			This is because our THI Cycle happens in odd Months (1,3,5,7,9 & 11). Hence, auditor mapping 
			list should by default list the auditors associated for the current cycle, If user wants to view 
			others then he must change the filters in List page. Logic written below is for default loading & 
			create page re-direction 
			********************************************************************************************************/
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int ctEndDate = 31;//default will be 31
			int ctMonth = cal.get(Calendar.MONTH) + 1;//In Java, Jan is 0 & Dec is 11
			int stMonth = ctMonth;
			int endMonth = ctMonth;

			//beginning of start date calculation[
			if( ctMonth%2 == 0 ){
				stMonth = ctMonth - 1;
			}
			/*#********* "BUG IN JAVA" **********************************************************************
			  Date.valueOf("2011-3-01")works fine until jre 1.6.10 where as the same one breaks in 1.6.18 
			  and thats the reason for adding this if block. This will add "0" in front of single digit months 
			  & Hence, call becomes Date.valueOf("2011-03-01") and it will work fine 
			 *#**********"BUG IN JAVA" ************************************************************************/
			String strStMonth = String.valueOf(stMonth); 
			if(  strStMonth.length() == 1 ){
				strStMonth = "0" + strStMonth;
			}
			startDateStr = year + "-" + strStMonth + "-01";
			//end of start date calculation]
			
			
			//beginning of end date calculation[
			if( ctMonth%2 == 1 ){
				endMonth = ctMonth + 1;
			}
			/*#********* "BUG IN JAVA" **********************************************************************
			  Date.valueOf("2011-3-01")works fine until jre 1.6.10 where as the same one breaks in 1.6.18 
			  and thats the reason for adding this if block. This will add "0" in front of single digit months 
			  & Hence, call becomes Date.valueOf("2011-03-01") and it will work fine 
			 *#**********"BUG IN JAVA" ************************************************************************/
			String strEndMonth = String.valueOf(endMonth); 
			if(  strEndMonth.length() == 1 ){
				strEndMonth = "0" + strEndMonth;
			}

			switch (endMonth) {
			case 2:
				if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
					ctEndDate = 29;
				} else {
					ctEndDate = 28;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:	
				ctEndDate = 30;
				break;
			}
			
			endDateStr = year + "-" + strEndMonth + "-" + ctEndDate;
			//end of end date calculation]
			
			startDate = Date.valueOf(startDateStr);
			endDate = Date.valueOf(endDateStr);
			projectAuditors = auditorManager.searchProjectAuditors(null, startDate, endDate, false);

			/* ********Reset to mm/dd/yyyy for views ********* */
			startDateStr = "01/" + strStMonth + "/" + year;
			endDateStr = ctEndDate + "/" + strEndMonth + "/" + year;
			/* ********Reset to mm/dd/yyyy for views ******** */
		}
		
		if(thiName != null && thiName.trim().length() > 0) {
			modal.put("thiName", thiName);
		} else {
			modal.put("thiName", "");
		}

		if(startDateStr != null && startDateStr.trim().length() > 0) {
			modal.put("startDateStr", startDateStr);
		} else {
			modal.put("startDateStr", "");
		}

		if(endDateStr != null && endDateStr.trim().length() > 0) {
			modal.put("endDateStr", endDateStr);
		} else {
			modal.put("endDateStr", "");
		}
		modal.put("auditComplete", auditComplete);
		modal.put("auditors", auditors);

        //sorting
        
		ProjectAuditorComparator comparator = ProjectAuditorComparator.getInstance();
        comparator.setSortByName(sortByName);
        comparator.setSortDirection(sortDirection);
        
        if(sortByName != null && sortByName.trim().length() > 0) {
        	modal.put("sortByName", sortByName);
        	Collections.sort(projectAuditors, comparator);
        	if(sortByName.equals("Auditor")) {
        		thiNameSortDir = "asc";
        		if(sortDirection.equals("asc")) {
            		auditorSortDir = "desc";
        		} else {
        			auditorSortDir = "asc";
        		}
        	} else {
        		auditorSortDir = "asc";
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
        int toIndex = ((pagination * 20) > projectAuditors.size()) ? projectAuditors.size() : (pagination * 20);

//		for (ProjectAuditor projectAuditor : projectAuditors.subList(fromIndex,  toIndex)) {
//			ProjectAuditorHelper projAuditor = ProjectAuditorHelper.convertProjectAuditor(projectAuditor);
//			projAuditor.setProjectName(this.projectManager.getProjectByID(projectAuditor.getProjectId()).getProjectName());
//			projAuditor.setAuditorName(this.auditorManager.getAuditorByID(projectAuditor.getAuditorId()).getName());
//			projAuditors.add(projAuditor);
//		}
		
        if (fromIndex <= 0) {
        	showPrev = true;
        }
        if (toIndex >= projectAuditors.size()) {
        	showNext = true;
        }
        modal.put("auditorSortDir", auditorSortDir);
        modal.put("thiNameSortDir", thiNameSortDir);
        modal.put("showPrev", showPrev);
        modal.put("showNext", showNext);
        modal.put("pagination", pagination);
		modal.put("proejctauditors", ((fromIndex < toIndex) ? projectAuditors.subList(fromIndex,  toIndex) : projectAuditors));
		return new ModelAndView("projectauditor", "modal", modal);
	}
}
