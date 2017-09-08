package com.aspire.thi.ws.client;

public interface ProsWebService {

	public String GetAllActiveCustomer();

	public String GetAllActiveProjects();

	public String GetAllDeactivateCustomer();

	public String GetAllProjects();

	public String GetProjectResourceAllocationAndProjectDetailsByAceNumber(
			String ACENumber);

//	public String GetProjectResourceAllocationAndProjectDetailsByAceNumber1();

	public String GetProjectResourceAllocationAndProjectDetailsByUserStringID(
			String userStringID);

	public String GetProjectResourceByProjectName(String projectName);

	public String GetProjectsByDeliveryUnitstringID(String deliveryUnitStringID);

	public String GetResourceByCustomerName(String customerName);

	public String GetResourceLentDetailByACENumber(String aceNumber);

	public String GetResourceLentDetailByUserStringID(String userStringID);

}
