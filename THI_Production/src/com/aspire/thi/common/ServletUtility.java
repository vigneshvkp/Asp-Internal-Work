/*
 * Created on Dec 26, 2007
 * 
 * Copyright 2007-2008 Aspire Systems Pvt. Ltd. All rights reserved.
 */

package com.aspire.thi.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to deal with servlet API
 * 
 * @author venkat.sadasivam
 */
public final class ServletUtility {

	private ServletUtility() {
		throw new Error("Contains only static methods");
	}

	public static String getCookie(final HttpServletRequest request, final String cookieName) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Returns the base url from the request
	 * 
	 * @param request
	 * @return
	 */
	public static String getBaseUrl(final HttpServletRequest request) {
		String baseURL = request.getRequestURL()
				.substring(0, request.getRequestURL().indexOf(request.getContextPath()));
		return baseURL + request.getContextPath();
	}

}
