package com.aspire.thi.utils;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.aspire.thi.common.ResourceUtility;
import com.aspire.thi.domain.AssesmentGroupScore;
import com.aspire.thi.service.ThiManager;
import com.aspire.thi.web.SaveThiAuditController;

/**
 * 
 * @author shankari.sakthivel
 *  Util class to calculate score
 *
 */
public class ScoringUtil {

    private  ThiManager thiManager;
    private Properties scoringProperties;
    private final Integer UXP = 3;
    private final Integer DEV = 1;
	public  void setThiManager(ThiManager thiManager) {
		this.thiManager = thiManager;
	}

	public  ThiManager getThiManager() {
		return thiManager;
	}

	protected final Logger logger = Logger.getLogger(ScoringUtil.class);
	/**
	 * read score from properties file based on assessment group type
	 * @param assessmentName
	 * @return
	 */
	private  double getScoreForAssessmentType(String assessmentName){
		double assessmentWeightage = 0;
		try{
			if (scoringProperties.containsKey(assessmentName)) {
				assessmentWeightage =Double.parseDouble(scoringProperties.getProperty(assessmentName));
			} 
		}catch(NullPointerException nullPointerException){
			logger.info(nullPointerException.getMessage());
		}catch(Exception exception){
			logger.info(exception.getMessage());
		}
		return assessmentWeightage;
		
	}
	
	/**
	 * read weightage for the not available sections
	 * @param groupScores
	 * @return
	 */
	private  double getNotAvailableAssessmentWeightage(List<AssesmentGroupScore> groupScores){
		double assessmentWeightage = 0.0;
		for (AssesmentGroupScore groupScore : groupScores) {
			if(groupScore.getScore() == -1){
					assessmentWeightage += getScoreForAssessmentType(getAssessmentGroupName(groupScore.getAssesmentGroupId()));
			}
		}
		return assessmentWeightage;
	
	}
	
	/**
	 * split weightage based on not available scores 
	 * @param assessmentName
	 * @param assessmentWeightage
	 * @return
	 */
	private  double calcualteWeightage(String assessmentName,double assessmentWeightage){
		double revisedWeightage;
		revisedWeightage = getScoreForAssessmentType(assessmentName) * ((assessmentWeightage / (1 - assessmentWeightage))+1); //split wieghtage and multiply with group weightage
		return revisedWeightage;
		
	}
	
	/**
	 * calculate overall score 
	 * @param groupScores
	 * @return
	 */
	public  double calculateOverallScore(List<AssesmentGroupScore> groupScores ,Integer assesmentType) {
		int maxScore = 3;
		double totalScore = 0;
		double score = 0;
		if (assesmentType.equals(UXP)) {
			scoringProperties = ResourceUtility.loadPropertiesFromWebPath("/WEB-INF/scoring_uxp.properties");
		}else{
			scoringProperties = ResourceUtility.loadPropertiesFromWebPath("/WEB-INF/scoring.properties");
		}
		double assessmentWeightage = getNotAvailableAssessmentWeightage(groupScores);
		for (AssesmentGroupScore groupScore : groupScores) {
				if (groupScore.getScore() >1 && groupScore.getScore() <= maxScore) {
					score = groupScore.getScore()*calcualteWeightage(getAssessmentGroupName(groupScore.getAssesmentGroupId()),assessmentWeightage);
					totalScore += score;
				}
				//if score is 1 , calculate with log 1
				else if (groupScore.getScore() == 1) {
					score =getLog(groupScore.getScore())*calcualteWeightage(getAssessmentGroupName(groupScore.getAssesmentGroupId()),assessmentWeightage);
					totalScore += score;
				}
		}
		if (totalScore <= 0) {
			return Double.valueOf("0");
		}
		return totalScore;
	}
	
	/**
	 * get assessment group name from db
	 * @param assessmentGroupId
	 * @return
	 */
	private String getAssessmentGroupName(Integer assessmentGroupId){
		return thiManager.getAssessmentGroupName(assessmentGroupId);
		
	}
	
	private double getLog(double score){
		double log = Math.log10(6.0);
		return log;
	}
	
	public double getWeitage(String assessmentName){
		
		double assessmentWeightage = 0;
		scoringProperties = ResourceUtility.loadPropertiesFromWebPath("/WEB-INF/scoring_LI.properties");

		try{
			if (scoringProperties.containsKey(assessmentName)) {
				assessmentWeightage =Double.parseDouble(scoringProperties.getProperty(assessmentName));
			} 
		}catch(NullPointerException nullPointerException){
			logger.info(nullPointerException.getMessage());
		}catch(Exception exception){
			logger.info(exception.getMessage());
		}
		return assessmentWeightage;
	}
	
	
}
