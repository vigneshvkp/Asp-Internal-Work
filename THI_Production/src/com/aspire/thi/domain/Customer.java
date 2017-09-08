package com.aspire.thi.domain;

public class Customer extends DatabaseObject{
	
	private String customerName;
	private boolean active;
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("Customer [customerName=").append(customerName)
				.append(", active=").append(active).append("]");
		return builder.toString();
	}
	
	

}
