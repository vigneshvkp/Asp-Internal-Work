package com.aspire.thi.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * The Class AssesmentGroupScore.
 */
public class AssesmentGroupScore extends AssesmentGroup {

	//vkp
	private double score;

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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}




}
