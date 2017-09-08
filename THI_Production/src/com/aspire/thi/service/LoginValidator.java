package com.aspire.thi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aspire.thi.domain.LoginCredentials;

public class LoginValidator implements Validator {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {
		return LoginCredentials.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		LoginCredentials loginCredentials = (LoginCredentials) obj;
		if (loginCredentials == null) {
			errors.rejectValue("userName", "login.value-not-specified", null,
					"Values Required for User Name and Password.");
		} else {
			String userName = loginCredentials.getUserName();
			String password = loginCredentials.getPassword();
			logger.info("Validating with " + loginCredentials + ": "
					+ loginCredentials.getUserName());

			
			if (userName.trim().length() == 0
					|| userName.contains(".") == false) {
				errors.rejectValue("userName", "login.username.invalid",
						new Object[] { userName }, "Invalid Username!");
			}
			
			if (password.trim().length() == 0) {
				errors.rejectValue("password", "login.password.invalid",
						new Object[] { password }, "Password cannot be empty.");
			}
		}
	}

}