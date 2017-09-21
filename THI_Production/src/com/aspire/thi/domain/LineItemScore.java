package com.aspire.thi.domain;

public class LineItemScore {

	private Integer id;
	private Integer thi_score_id;
	private double score;
	private Integer ass_line_item_id;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getThi_score_id() {
		return thi_score_id;
	}


	public void setThi_score_id(Integer thi_score_id) {
		this.thi_score_id = thi_score_id;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	public Integer getAss_line_item_id() {
		return ass_line_item_id;
	}


	public void setAss_line_item_id(Integer ass_line_item_id) {
		this.ass_line_item_id = ass_line_item_id;
	}


	public LineItemScore() {
		super();
	}


	
}
