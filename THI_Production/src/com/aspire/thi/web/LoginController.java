package com.aspire.thi.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.aspire.thi.domain.LoginCredentials;
import com.aspire.thi.domain.UserDetail;
import com.aspire.thi.service.AuthenticationManager;

@SuppressWarnings("deprecation")
public class LoginController extends SimpleFormController {

	private AuthenticationManager authenticationManager;

	private static final Logger LOGGER = Logger
			.getLogger(LoginController.class);

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		LoginCredentials loginCrendentails = (LoginCredentials) command;
		String userName = loginCrendentails.getUserName();
		//String password = loginCrendentails.getPassword();
		LOGGER.info("Authenticating users with LDAP for userName" + userName);
		// + "and Password " + password);

		boolean validUser = authenticationManager.authenticate(loginCrendentails);

		if (validUser == false) {
			errors.rejectValue("userName", "login.invalid.user",
					"Invalid User Credentials");
			LOGGER.info(userName + " is invalid User.");
			return new ModelAndView(new RedirectView("login.htm"));
		}

		LOGGER.info("User is valid one, now loading complete user details");
		UserDetail userDetail = authenticationManager.loadUser(userName);
		HttpSession session = request.getSession();
		session.setAttribute("UserDetail", userDetail);
		LOGGER.info("Complete User Detail has been loaded and set into session with attribute name as UserDetail = "
				+ session.getAttribute("UserDetail"));

		RedirectView view = null;
		if (userDetail.isAuditor()) {
			view = new RedirectView("loadAuditProjects.htm");
			LOGGER.info("returning from Login View to loadAuditProjects.htm");
		} else {
			view = new RedirectView(getSuccessView());
			LOGGER.info("returning from Login View to " + getSuccessView());
		}
		return new ModelAndView(view);
		// return new ModelAndView(getSuccessView());
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		LoginCredentials loginCredentials = new LoginCredentials();
		loginCredentials.setUserName(request.getParameter("userName"));
		loginCredentials.setPassword(request.getParameter("password"));
		return loginCredentials;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

}
