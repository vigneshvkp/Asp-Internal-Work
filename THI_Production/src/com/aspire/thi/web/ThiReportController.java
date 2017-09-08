package com.aspire.thi.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.aspire.thi.domain.ProjectAuditor;
import com.aspire.thi.domain.ThiScore;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.ThiManager;
import com.aspire.thi.utils.THIPdfReportCreator;

public class ThiReportController implements Controller {

	private static final int DEFAULT_BUFFER_SIZE = 10240;

	private static final String VIEW_NAME = "thiReport";

	private static final String MODEL = "thiScore";

	private static final String PARAM_AUDIT_CYCLE = "auditCycleDate";

	private static final String PARAM_PROJECT_ID = "projectId";

	private static final String EXPORT_AS_PDF = "exportAsPdf";

	private static final String AJAX_LOGIN = "ajaxLogin";

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
		UserDetail userDetail = (UserDetail) request.getSession().getAttribute("UserDetail");
		if (userDetail != null) {
			ThiScore thiScore = null;
			String projectIdStr = request.getParameter(PARAM_PROJECT_ID);
			String exportAsPdf = request.getParameter(EXPORT_AS_PDF);
			String auditDate = request.getParameter(PARAM_AUDIT_CYCLE);
			if (StringUtils.isBlank(projectIdStr) == Boolean.FALSE && StringUtils.isNumeric(projectIdStr)
					&& StringUtils.isBlank(auditDate) == Boolean.FALSE && auditDate.split("/").length == 2) {
				Integer projectId = Integer.valueOf(projectIdStr);
				Calendar cal = Calendar.getInstance();
				String[] auditCycle = auditDate.split("/");
				cal.set(Calendar.MONTH, Integer.valueOf(auditCycle[0]) - 1);
				cal.set(Calendar.YEAR, Integer.valueOf(auditCycle[1]));
				ProjectAuditor projectAudtor = this.thiManager.getProjectAuditor(projectId, cal.getTime());
				if (projectAudtor != null && projectAudtor.getAuditComplete()) {
					thiScore = this.thiManager.getThiScore(projectId, cal.getTime());
				}
			}
			if (exportAsPdf != null && "true".equals(exportAsPdf)) {
				writePdfResponse(thiScore, userDetail, response);
				return null;
			} else {
				return new ModelAndView(VIEW_NAME, MODEL, thiScore);
			}
		} else {
			return new ModelAndView(AJAX_LOGIN);
		}
	}

	private void writePdfResponse(ThiScore thiScore, UserDetail userDetail, HttpServletResponse response)
			throws Exception {
		THIPdfReportCreator creator = new THIPdfReportCreator(thiScore);
		String fileName = creator.generatePdf();
		if (fileName != null) {
			// Prepare streams.
			BufferedInputStream input = null;
			BufferedOutputStream output = null;

			try {
				// Open streams.
				input = new BufferedInputStream(new FileInputStream(fileName), DEFAULT_BUFFER_SIZE);
				output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

				// Write file contents to response.
				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
			} finally {
				// Gently close streams.
				close(output);
				close(input);
				if (fileName != null) {
					creator.deleteFile(fileName);
				}
			}
		}

	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail it.
				e.printStackTrace();
			}
		}
	}

}