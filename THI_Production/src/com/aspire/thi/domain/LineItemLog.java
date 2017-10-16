package com.aspire.thi.domain;

public class LineItemLog extends AssesmentLineItem {

	private String comments;
	private Integer score;
	private Integer lineItemId;
	private String percentage;

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

}
