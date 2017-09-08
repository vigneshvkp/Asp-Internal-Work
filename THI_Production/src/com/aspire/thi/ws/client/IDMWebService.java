package com.aspire.thi.ws.client;

public interface IDMWebService {
	
	//public String GetAllDeliveryUnit();
	public String GetAllDepartment();
	public String GetAllEmployeeType();
	//public String GetAllDepartmentDesignation();
	public String GetAllDesignation();
	public String GetAllReportingRelation();
	public String GetAllLevel();
	public String GetEmployeeDetailByUserName(String date, String userName);
	public String GetEmployeeDetailByUserStringID(String date, String userStringID);
	public String GetEmployeeListByUserStringID(String date, String userStringID, int relationid);
	public String GetEmployeeDetail(String date, String aceNumber);
	//input is comma separated acenos, each aceno should not be enclosed in quotes
	public String GetAllEmployeeDetailsByUserACENumbers(String userACENumbers);
	
//	public String To_get_UserRelations_with_other_users_for_the_given_userACENumber(String loginUserACENumber, int relationid);

}
