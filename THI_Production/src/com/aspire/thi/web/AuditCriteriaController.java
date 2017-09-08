package com.aspire.thi.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.aspire.thi.service.ThiManager;


public class AuditCriteriaController implements Controller {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	private ThiManager thiManager;


	public void setThiManager(ThiManager thiManager) {
		this.thiManager = thiManager;
	}

	public ThiManager getThiManager() {
		return thiManager;
	}



	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String assesmentGrpIdStr = request.getParameter("assesmentGroupId");
		List<String> criteria = null;
		if (StringUtils.isBlank(assesmentGrpIdStr) == Boolean.FALSE && StringUtils.isNumeric(assesmentGrpIdStr)) {
			criteria = this.thiManager.getAssesmentCriteria(Integer.valueOf(assesmentGrpIdStr));
		}
		return new ModelAndView("criteria", "criterias", criteria);
	}

}