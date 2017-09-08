/*
 * Created on Jan 23, 2008
 * 
 * Copyright 2007-2008 Aspire Systems Pvt. Ltd. All rights reserved.
 */

package com.aspire.thi.web.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.log4j.Logger;

/**
 * Tag to check the permission
 * 
 * @author venkat.sadasivam
 */

@SuppressWarnings("unused")
public class PermissionTag extends AbstractLogicTag {

	private static final Logger logger = Logger.getLogger(PermissionTag.class);

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private String expression;

	private static final Map<String, Expression> expressionCache = new HashMap<String, Expression>();

	/**
	 * @see com.aspire.propel.web.common.AbstractLogicTag#initializeFields()
	 */
	@Override
	protected void initializeFields() {
		expression = null;
	}

	/**
	 * @see com.aspire.propel.web.common.AbstractLogicTag#isLogicValid()
	 */
	@Override
	protected boolean isLogicValid() throws JspException {
		return parseExp(pageContext, expression);
	}

	public static boolean parseExp(final PageContext pageContext, final String expString) throws JspException {
//		Subject subject = (Subject) pageContext.getRequest().getAttribute(SecurityInterceptor.PARAM_SUBJECT);
//		if (subject != null) {
//			UserPrincipal userPrincipal = UserPrincipal.get(subject);
//			JexlContext jexlContext = (JexlContext) pageContext.getRequest().getAttribute("jexlContext");
//			if (jexlContext == null) {
//				jexlContext = JexlHelper.createContext();
//				for (PermissionConstants p : PermissionConstants.values()) {
//					jexlContext.getVars().put(p.name(), userPrincipal.getUserDetail().hasPermission(p));
//				}
//				pageContext.getRequest().setAttribute("jexlContext", jexlContext);
//			}
//			return evaluate(expString, jexlContext);
//		}
//		return false;
		return true;
	}

	/**
	 * Parses the given permission expression and returns a boolean value
	 * 
	 * @param expString
	 * @return
	 * @throws JspException
	 */
	private static boolean evaluate(final String expString, final JexlContext jexlContext) throws JspException {
//		long startTime = System.currentTimeMillis();
		try {
			Expression expression = expressionCache.get(expString);
			if (expression == null) {
				expression = ExpressionFactory.createExpression(expString);
				expressionCache.put(expString, expression);
			}
			// evaluate the expression, get the result
			Boolean result = (Boolean) expression.evaluate(jexlContext);
			if (result != null)
				return result.booleanValue();
		} catch (Exception e) {
			throw new JspException(e);
		} finally {
			if (logger.isDebugEnabled()) {
				long endTime = System.currentTimeMillis();
				//LOGGER.debug("Time taken to parse exp " + expString + " is: " + (endTime - startTime) + "msec");
			}
		}
		return false;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(final String expression) {
		this.expression = expression;
	}
}
