package com.aspire.thi.utils;

public class MailNotSentException extends Exception {

	private static final long serialVersionUID = 2275351540215162760L;

	private int errorNumber;

	/**
	 *
	 */
	public MailNotSentException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public MailNotSentException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public MailNotSentException(int errorNumber, String arg0) {
		super(arg0);
		this.errorNumber = errorNumber;
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MailNotSentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public MailNotSentException(Throwable arg0) {
		super(arg0);
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}
}
