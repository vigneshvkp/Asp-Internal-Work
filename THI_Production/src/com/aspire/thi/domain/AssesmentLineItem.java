package com.aspire.thi.domain;

public class AssesmentLineItem extends DatabaseObject {

	private String text;

	private String description;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
