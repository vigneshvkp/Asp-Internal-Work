package com.aspire.thi.domain;

public class LineItemLog extends AssesmentLineItem {

	private String comments;

	private Integer lineItemId;

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setLineItemId(Integer lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Integer getLineItemId() {
		return lineItemId;
	}
}
