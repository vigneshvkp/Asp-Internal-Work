package com.aspire.thi.domain;

import java.util.List;

/**
 * The Class AssesmentGroupScore.
 */
public class AssesmentGroupScore extends AssesmentGroup {

	//vkp
	private double score;

	private List<LineItemLog> lineItemLogs;

	private Integer assesmentGroupId;
	
	//vkp
	private List<LineItemScore> lineItemScores;

	private List<Weitage> weight;
	


	public List<Weitage> getWeight() {
		return weight;
	}

	public void setWeight(List<Weitage> weight) {
		this.weight = weight;
	}

	public List<LineItemScore> getLineItemScores() {
		return lineItemScores;
	}

	//vkp
	public void setLineItemScores(List<LineItemScore> lineItemScores) {
		this.lineItemScores = lineItemScores;
	}

	//vkp
	public void setWeitage(List<Weitage> weights){
		this.weight=weights;
		
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}




}
