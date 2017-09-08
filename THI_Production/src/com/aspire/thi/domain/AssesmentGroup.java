package com.aspire.thi.domain;

public class AssesmentGroup extends DatabaseObject {

	private String name;

	private String description;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
