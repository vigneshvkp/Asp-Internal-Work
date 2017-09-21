package com.aspire.thi.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.aspire.thi.common.THIContextLoaderListener;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.AuthenticationManager;
import com.aspire.thi.service.ThiManager;
import com.aspire.thi.utils.ScoringUtil;
import com.aspire.thi.utils.THIPdfReportCreator;

@SuppressWarnings("deprecation")
public class SaveThiAuditController extends SimpleFormController {

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private static final String AJAX_LOGIN = "ajaxLogin";
	/** Logger for this class and subclasses */
	protected final Logger logger = Logger.getLogger(SaveThiAuditController.class);

	private ThiManager thiManager;

	private AuthenticationManager authenticationManager;
	
	private ScoringUtil scoringUtil;

	public void setThiManager(ThiManager thiManager) {
		this.thiManager = thiManager;
	}

	public ThiManager getThiManager() {
		return thiManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public ScoringUtil getScoringUtil() {
		return scoringUtil;
	}

	public void setScoringUtil(ScoringUtil scoringUtil) {
		this.scoringUtil = scoringUtil;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		String otherAceNos = request.getParameter("otheracenos");
		System.out.println("other acenos.........: "+otherAceNos);
		String[] auditees = null;
		if((otherAceNos!=null)&&(!otherAceNos.equals(""))){
			auditees = otherAceNos.split("-");
		}
		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		ModelAndView model = null;
		if (userDetail != null) {
			ThiScore audit = (ThiScore) command;
			double overallScore = scoringUtil.calculateOverallScore(audit.getAssesmentGroupScores(), audit.getAssesmentType());
			audit.setOverallScore(overallScore);
			if (audit.getId() > 0) {
				audit.setLastModifiedBy(userDetail.getAceNo());
				thiManager.updateAudit(audit);
				//thiManager.insertAuditee(audit.getProjectId(), auditees,userDetail.getAceNo());
			} else {
				ThiScore thiScore = thiManager.getThiScore(audit.getProjectId(), audit.getAuditCycleDate());
				if (thiScore != null) {
					audit.setLastModifiedBy(userDetail.getAceNo());
					thiManager.updateAudit(audit);
					//thiManager.insertAuditee(audit.getProjectId(), auditees,userDetail.getAceNo());
				} else {
					audit.setAuditorId(userDetail.getAuditorId());
					audit.setCreatedBy(userDetail.getAceNo());
					audit.setLastModifiedBy(userDetail.getAceNo());
					thiManager.saveAudit(audit);
					//thiManager.insertAuditee(audit.getProjectId(), auditees,userDetail.getAceNo());
					
				}
			}
			//insert the selected auditees acenos to project_auditee_mapping table
			if((auditees!=null)&&(auditees.length>0)){
				thiManager.insertAuditee(audit.getProjectId(), auditees,userDetail.getAceNo());
			}
			if (audit.getAuditComplete()) {
				thiManager.setAuditComplete(Boolean.TRUE, audit.getProjectId(), audit.getAuditorId(),
						audit.getAuditCycleDate());
				ThiScore thiScore = thiManager.getThiScore(audit.getProjectId(), audit.getAuditCycleDate());
				THIPdfReportCreator reportCreator = new THIPdfReportCreator(thiScore);
				List<String> emailAddress = new ArrayList<String>();
				//enable before live
/*				UserDetail projectOwnerDetail = authenticationManager.loadUserByStringId(audit.getProjectOwnerId());
				emailAddress.add(projectOwnerDetail.getEmail());
*/				//Directly retrieve the project owner e-mail
				String ownerEmail = authenticationManager.getProjectOwnerEmail(audit.getProjectOwnerId());
				if(ownerEmail == null){
					ownerEmail="Vignesh.Murugesan@aspiresys.com";
				}
				if(ownerEmail.trim().length() > 0 ){
					emailAddress.add(ownerEmail);
				}
				String schedulerEmail = authenticationManager.getProjectAuditSchedulerEmail(audit.getProjectId());
				if(schedulerEmail.trim().length() > 0 ){
					emailAddress.add(schedulerEmail);
				}
				System.out.println("Scheduler email:"+schedulerEmail);
				emailAddress.add(userDetail.getEmail());//user email
				//enable before live
				String qaLeadAddress = THIContextLoaderListener.getQALead();
				if (qaLeadAddress != null) {
					emailAddress.add(qaLeadAddress);
				}
				reportCreator.sendMail(emailAddress);
				//To make sure this audit removed from session
				userDetail.getAuditProjectIds().remove(audit.getProjectId());
			}
			model = super.onSubmit(request, response, command, errors);
		} else {
			model = new ModelAndView("login");
		}
		return model;

	}

	
}