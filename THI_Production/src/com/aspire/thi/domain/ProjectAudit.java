/**
 * 
 */
package com.aspire.thi.domain;
/**
 * @author berkin.moyeesan
 *
 */
public class ProjectAudit extends Project {

	/**
	 * 
	 */
	private ProjectAuditor projectAuditor = null;
	public ProjectAudit() {
		// TODO Auto-generated constructor stub
	}
	public ProjectAuditor getProjectAuditor() {
		return projectAuditor;
	}
	public void setProjectAuditor(ProjectAuditor projectAuditor) {
		this.projectAuditor = projectAuditor;
	}
	
	
	
}
