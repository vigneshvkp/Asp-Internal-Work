package com.aspire.thi.domain;

import java.util.List;

/**
 * The Class AssesmentGroupScore.
 */
public class AssesmentGroupScore extends AssesmentGroup {

	private Integer score;

	private List<LineItemLog> lineItemLogs;

	private Integer assesmentGroupId;


	public void setLineItemLogs(List<LineItemLog> lineItemLogs) {
		this.lineItemLogs = lineItemLogs;
	}

	public List<LineItemLog> getLineItemLogs() {
		return lineItemLogs;
	}

	public void setAssesmentGroupId(Integer assesmentGroupId) {
		this.assesmentGroupId = assesmentGroupId;
	}

	public Integer getAssesmentGroupId() {
		return assesmentGroupId;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getScore() {
		return score;
	}
}
