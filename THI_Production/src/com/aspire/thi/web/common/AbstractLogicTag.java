/*
 * Created on Feb 3, 2006
 *
 * Copyright 2006 Aspire Systems Pvt. Ltd. All rights reserved.
 */

package com.aspire.thi.web.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Base class for all the Crux framework logic tags <br>
 * Logic tag is nothing but the tags used to validate certain condition. <br>
 * Usually logic tag will contain body
 * 
 * @author venkat.sadasivam
 */
public abstract class AbstractLogicTag extends TagSupport {

	/**
	 * Constructor for AbstractLogicTag.
	 */
	public AbstractLogicTag() {
		super();
		this.initializeFields();
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public final int doStartTag() throws JspException {
		if (isLogicValid()) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public final int doEndTag() throws JspException {
		initializeFields();
		return super.doEndTag();
	}

	/**
	 * To initialize the attributes
	 */
	protected abstract void initializeFields();

	/**
	 * Subclasses has to override this method and should return true if the body
	 * needs to rendered; false otherwise
	 * 
	 * @return
	 */
	protected abstract boolean isLogicValid() throws JspException;

}
