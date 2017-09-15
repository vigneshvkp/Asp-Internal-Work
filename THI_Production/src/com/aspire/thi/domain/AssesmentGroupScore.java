package com.aspire.thi.domain;

import java.util.List;

/**
 * The Class AssesmentGroupScore.
 */
public class AssesmentGroupScore extends AssesmentGroup {

	private Integer score;

	private List<LineItemLog> lineItemLogs;

	private Integer assesmentGroupId;
	
	private List<LineItemScore> lineItemScores;

	private String vkp;
	
	

	public String getVkp() {
		return vkp;
	}

	public void setVkp(String vkp) {
		this.vkp = vkp;
	}

	public List<LineItemScore> getLineItemScores() {
		return lineItemScores;
	}

	public void setLineItemScores(List<LineItemScore> lineItemScores) {
		this.lineItemScores = lineItemScores;
	}

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
