package com.aspire.thi.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

import com.aspire.thi.domain.Department;
import com.aspire.thi.domain.ThiReport;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.DepartmentManager;
import com.aspire.thi.service.ThiReportManager;

@SuppressWarnings("deprecation")
public class OrgReportFormController extends SimpleFormController {

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
		model.put("userDetail", userDetail);
		return model;
	}

	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		DuReportForm reportForm = (DuReportForm) command;
		Map<String, Object> orgReport = new LinkedHashMap<String, Object>();
		List<Department> delivaryUnits = departmentManager.getDelivaryUnits();
		for (Department department : delivaryUnits) {
			List<ThiReport> duReport = thiReportManager.getOrgThiReport(department.getId(), reportForm.getAuditCycle());
			if (duReport != null && duReport.isEmpty() == Boolean.FALSE) {
				Map<String, List<ThiReport>> astGroupReport = getAssesmentTypeGroupReport(duReport);
				Map<String, ThiReport> avgGroupScoreByAstGroup = new LinkedHashMap<String, ThiReport>();
				if (astGroupReport != null && astGroupReport.isEmpty() == Boolean.FALSE) {
					Set<Entry<String, List<ThiReport>>> astGroupReportEntry = astGroupReport.entrySet();
					for (Entry<String, List<ThiReport>> entry : astGroupReportEntry) {
						ThiReport report = getAvgThiReport(entry.getValue());
						avgGroupScoreByAstGroup.put(entry.getKey(), report);
					}
					orgReport.put(department.getName(), avgGroupScoreByAstGroup);
				}
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orgReport", orgReport);
		model.put("reportForm", reportForm);
		return new ModelAndView("orgReport", model);
	}

	private ThiReport getAvgThiReport(List<ThiReport> value) {
		ThiReport avgReport = null;
		for (ThiReport thiReport : value) {
			if (avgReport == null) {
				avgReport = thiReport;
			} else {
				sumThiGroupScore(avgReport, thiReport);
			}

		}
		return avgReport;
	}

	private void sumThiGroupScore(ThiReport avgReport, ThiReport thiReport) {
		Map<String, Double> avgAstGroupScore = avgReport.getAssesmentGroupScore();
		Set<String> avgAstGroupKey = avgAstGroupScore.keySet();
		for (String key : avgAstGroupKey) {
			Double avgScore = avgAstGroupScore.get(key);
			Double newGrpScore = thiReport.getAssesmentGroupScore().get(key);
			if (newGrpScore != null) {
				if (avgScore == -1 && newGrpScore != -1) {
					avgScore = newGrpScore;
				} else if (avgScore != -1 && newGrpScore != -1) {
					avgScore = (avgScore + newGrpScore) / 2;
				}
			}
			avgAstGroupScore.put(key, avgScore);
		}
		Set<String> groupScoreKey = thiReport.getAssesmentGroupScore().keySet();
		for (String key : groupScoreKey) {
			if (avgAstGroupKey.contains(key) == Boolean.FALSE) {
				avgAstGroupScore.put(key, thiReport.getAssesmentGroupScore().get(key));
			}
		}
		avgReport.setOverallScore((avgReport.getOverallScore() + thiReport.getOverallScore()) / 2);

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