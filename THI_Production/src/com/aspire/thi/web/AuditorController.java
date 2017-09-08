package com.aspire.thi.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.domain.Auditor;
import com.aspire.thi.service.AuditorHelper;
import com.aspire.thi.service.AuditorManager;
import com.aspire.thi.utils.AppContext;
import com.aspire.thi.domain.UserDetail;

@SuppressWarnings("deprecation")
public class AuditorController extends SimpleFormController {
	public static String PIPE_SEPARATED_ACE_NO = "PipeSeparatedAceNo";
	public static String HYPHEN_SEPARATED_ACE_NO_REMOVE_AUDITOR = "HyphenSeparatedAceNoRemoveAuditor";
	protected final Log LOGGER = LogFactory.getLog(getClass());

	private AuditorManager auditorManager;
	
	@Override
	public ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		AuditorHelper auditorHelper = new AuditorHelper();
		try {
			List<String> newAuditorNos = new ArrayList<String>();
			for(String aceNo : StringUtils.split(request.getParameter(PIPE_SEPARATED_ACE_NO), '|')) {
				newAuditorNos.add(aceNo);
			}
			/*
			 * retrieve the string aceno-username pairs separated by hyphen
			 * using aceno update the auditor table and set active=0 if there is no incomplete audit exists
			 * else no update will take place
			 */
			String removeAceNos = request.getParameter(HYPHEN_SEPARATED_ACE_NO_REMOVE_AUDITOR);
			String[] removeAuditors = null;
			if((removeAceNos!=null)&&(!removeAceNos.trim().equals(""))){
				removeAuditors = removeAceNos.split("---");
			}
			UserDetail userDetail = (UserDetail)request.getSession().getAttribute("UserDetail");
			if(userDetail == null){
				return new ModelAndView(new RedirectView("login.htm"));
			}
			boolean removeAuditorFlag = false;
			StringBuffer msgSB = new StringBuffer(6400);
			if(removeAuditors!=null){
				removeAuditorFlag = true;
				for(int i=0;i<removeAuditors.length;++i){
					String[] aceNo_userName = removeAuditors[i].split("--");
					String msg = auditorManager.removeAuditor(aceNo_userName[0], userDetail.getAceNo());
					msgSB.append(msg+" ("+aceNo_userName[1]+")"+"<br/>");
				}
			}
			List<Auditor> auditors = auditorManager.getAuditorList();
			for(Auditor auditor : auditors) {
				newAuditorNos.remove(auditor.getAceNo());
			}
			boolean addAuditorFlag = false;
			Auditor auditor = null;
			for(String aceNo : newAuditorNos) {
				addAuditorFlag = true;
				auditor = auditorManager.loadUser(aceNo);
				auditor.setActive(true);
				auditorManager.createAuditor(auditor);
			}
			if(removeAuditorFlag && addAuditorFlag){
				System.out.println("both remove and add");
				auditorHelper.setMsg("Auditor successfully added\n"+msgSB.toString());
			}else if(addAuditorFlag){
				System.out.println("adding auditor");
				auditorHelper.setMsg("Auditor successfully added");
			}else if(removeAuditorFlag){
				System.out.println("remove auditor "+msgSB.toString());
				auditorHelper.setMsg(msgSB.toString());
			}
			//auditorHelper.setMsg("Auditor successfully added");
		} catch(Exception exception) {
			LOGGER.error("Error occured while saving data :" + exception.getMessage());
			exception.printStackTrace();
			auditorHelper.setMsg("Auditor not saved");
		} finally {
			getModelObjects(auditorHelper);
		}
		
		return new ModelAndView("addAuditor", "modal", auditorHelper);
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
			throws Exception {
		LOGGER.info("Start showForm in AuditorController");
		AuditorHelper auditorHelper = new AuditorHelper();
		getModelObjects(auditorHelper);
		auditorHelper.setMsg("");
		return new ModelAndView("addAuditor", "modal", auditorHelper);
	}
	
	private void getModelObjects(AuditorHelper auditorHelper) {
		List<Auditor> allEmp = AppContext.loadAllEmployeeByRole();
		List<Auditor> auditors = auditorManager.getAuditorList();
		allEmp.removeAll(auditors);
		auditorHelper.setEmps(allEmp);
		auditorHelper.setAuditors(auditors);
	}
	
	public AuditorManager getAuditorManager() {
		return auditorManager;
	}

	public void setAuditorManager(AuditorManager auditorManager) {
		this.auditorManager = auditorManager;
	}
}

