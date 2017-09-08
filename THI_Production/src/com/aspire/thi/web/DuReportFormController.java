package com.aspire.thi.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.domain.ThiReport;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.DepartmentManager;
import com.aspire.thi.service.ThiReportManager;

@SuppressWarnings("deprecation")
public class DuReportFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private ThiReportManager thiReportManager;

	private DepartmentManager departmentManager;

	private static final String AJAX_LOGIN = "ajaxLogin";

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		if (userDetail != null) {
			return super.handleRequest(request, response);
		} else {
			if (request.getMethod().equalsIgnoreCase("post")) {
				return new ModelAndView(AJAX_LOGIN);
			} else {
				return new ModelAndView(new RedirectView("login.htm"));
			}
		}

	}


	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request, Object command, Errors errors)
			throws Exception {

		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		Map<String, Object> model = new HashMap<String, Object>();
		if (userDetail != null) {
			model.put("delivaryUnits", departmentManager.getDelivaryUnits());
		}
		model.put("userDetail", userDetail);
		return model;
	}

	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		DuReportForm reportForm = (DuReportForm) command;
		Map<String, List<ThiReport>> astGroupReport = null;
		List<ThiReport> duReport = null;
		duReport = thiReportManager.getDUThiReport(reportForm.getDepartmentId(), reportForm.getAuditCycle(), reportForm.getToDate());
		if (duReport != null && duReport.isEmpty() == Boolean.FALSE) {
			astGroupReport = getAssesmentTypeGroupReport(duReport);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("duReport", astGroupReport);
		model.put("reportForm", reportForm);
		return new ModelAndView("duReport", model);
	}

	private Map<String, List<ThiReport>> getAssesmentTypeGroupReport(List<ThiReport> duReport) {
		Map<String, List<ThiReport>> astGroupReport = new LinkedHashMap<String, List<ThiReport>>();
		for (ThiReport thiReport : duReport) {
			List<ThiReport> groupRpt = astGroupReport.get(thiReport.getAssesmentType());
			if (groupRpt == null) {
				groupRpt = new ArrayList<ThiReport>();
				astGroupReport.put(thiReport.getAssesmentType(), groupRpt);
			}
			groupRpt.add(thiReport);
		}
		return astGroupReport;
	}

	public void setThiReportManager(ThiReportManager thiReportManager) {
		this.thiReportManager = thiReportManager;
	}

	public ThiReportManager getThiReportManager() {
		return thiReportManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

}