package com.aspire.thi.domain;

/**
 * The Class AssesmentGroupCheckList.
 */
public class AssesmentGroupCheckList extends DatabaseObject {

	/** The description. */
	private String description;

	/** The heading. */
	private String heading;

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the heading.
	 * 
	 * @param heading
	 *            the new heading
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * Gets the heading.
	 * 
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

}
