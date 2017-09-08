/**
 * 
 */
package com.aspire.thi.ws.client;

import java.rmi.Remote;
//import java.rmi.RemoteException;

/**
 * @author muthu.velappan
 * 
 */
public interface IDMRemote extends Remote {
	

	//public java.lang.String GetAllDeliveryUnit();
	public java.lang.String GetAllDepartment() throws java.rmi.RemoteException;
	public java.lang.String GetAllEmployeeType() throws java.rmi.RemoteException;
	//public java.lang.String GetAllDepartmentDesignation();
	public java.lang.String GetAllDesignation() throws java.rmi.RemoteException;
	public java.lang.String GetAllReportingRelation() throws java.rmi.RemoteException;
	public java.lang.String GetAllLevel()throws java.rmi.RemoteException;
	public java.lang.String GetEmployeeDetailByUserName(java.lang.String date, java.lang.String userName)throws java.rmi.RemoteException;
	public java.lang.String GetEmployeeDetailByUserStringID(java.lang.String date,
			java.lang.String userStringID) throws java.rmi.RemoteException;
	public java.lang.String GetEmployeeDetail(java.lang.String date, java.lang.String aceNumber) throws java.rmi.RemoteException;
	public java.lang.String GetEmployeeListByUserStringID(java.lang.String date, java.lang.String userStringID, int relationid) throws java.rmi.RemoteException;
	
//	public String To_get_UserRelations_with_other_users_for_the_given_userACENumber(String loginUserACENumber, int relationid);


    public java.lang.String getAllEmployeesByBirthdate(java.lang.String month, java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String getAllWorkRoles() throws java.rmi.RemoteException;
    public void insertResourcelent(java.lang.String xmlString) throws java.rmi.RemoteException;
    public void updateResourcelent(java.lang.String xmlString) throws java.rmi.RemoteException;
    public java.lang.String getUserCoDepartmentByStringID(java.lang.String resoureLentID) throws java.rmi.RemoteException;
    public void insertUserReportingTo(java.lang.String xmlString) throws java.rmi.RemoteException;
    public void updateUserReportingTo(java.lang.String xmlString) throws java.rmi.RemoteException;
    public java.lang.String getUserReportingToByResourceLentID(java.lang.String resourceLentID) throws java.rmi.RemoteException;
    public java.lang.String employeeHistory(java.lang.String aceNumber) throws java.rmi.RemoteException;
    //public java.lang.String getAllDepartment() throws java.rmi.RemoteException;
    public Object getAllEmployeeEffectiveDatesByFieldIdsAndDate(java.lang.String fieldIds, java.lang.String effectiveFromDate, java.lang.String effectiveToDate) throws java.rmi.RemoteException;
    public Object getAllIDM_UsersByDate(java.lang.String startDate, java.lang.String endDate) throws java.rmi.RemoteException;
    public java.lang.String getCoManagerReportingResourcesByLockDate(java.lang.String lockdate) throws java.rmi.RemoteException;
    public java.lang.String getAllCountry() throws java.rmi.RemoteException;
    //public java.lang.String getAllReportingRelation() throws java.rmi.RemoteException;
    //public java.lang.String getAllDesignation() throws java.rmi.RemoteException;
    public java.lang.String getAllDivision() throws java.rmi.RemoteException;
    //public java.lang.String getEmployeeDetail(java.lang.String date, java.lang.String aceNumber) throws java.rmi.RemoteException;
    //public java.lang.String getEmployeeDetailByUserStringID(java.lang.String date, java.lang.String userStringID) throws java.rmi.RemoteException;
    //public java.lang.String getEmployeeDetailByUserName(java.lang.String date, java.lang.String userName) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeList(java.lang.String date, java.lang.String userAceNumber, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getAllInActiveEmployeeList() throws java.rmi.RemoteException;
    //public java.lang.String getEmployeeListByUserStringID(java.lang.String date, java.lang.String userStringID, int relationid) throws java.rmi.RemoteException;
    public java.lang.String GetAllEmployeeDetailsByUserACENumbers(java.lang.String userACENumbers) throws java.rmi.RemoteException;
    public java.lang.String getAllEmployeeDetailsByUserStringIds(java.lang.String userStringIds) throws java.rmi.RemoteException;
    public java.lang.String getAllUserByDepartmentStringId(java.lang.String departmentStringId) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeList(java.lang.String xmlString) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeListForHelpdesk(java.lang.String date, java.lang.String userAceNumber, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeStatus() throws java.rmi.RemoteException;
    //public java.lang.String getAllEmployeeType() throws java.rmi.RemoteException;
    //public java.lang.String getAllLevel() throws java.rmi.RemoteException;
    public java.lang.String getAllLocationCenter() throws java.rmi.RemoteException;
    public java.lang.String getAllTrack() throws java.rmi.RemoteException;
    public java.lang.String getNameByACENumber(java.lang.String aceNumber) throws java.rmi.RemoteException;
    public java.lang.String getAllOfficeLocation() throws java.rmi.RemoteException;
    public java.lang.String[] getRelation(java.lang.String loginUserACENumber, java.lang.String userACENumber) throws java.rmi.RemoteException;
    public java.lang.String[] getRelation(java.lang.String aceNumber, int relationId) throws java.rmi.RemoteException;
    public java.lang.String getAllLockDate() throws java.rmi.RemoteException;
    public Object getAcquiredCompanyEmployeeDetailByAceNumber(java.lang.String aceNumber) throws java.rmi.RemoteException;
    public java.lang.String getAllEmployeeBasicDetailsList(java.lang.String userAceNumber, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getInActiveEmployeeBasicDetailsList() throws java.rmi.RemoteException;
    public Object getUserNameByPrefix(java.lang.String prefix) throws java.rmi.RemoteException;
    public java.lang.String getPersonalEmployeeList(java.lang.String date, java.lang.String userAceNumber, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getPersonalEmployeeDetails(java.lang.String date, java.lang.String userAceNumber, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getPersonalEmployeeDetailsByUserName(java.lang.String date, java.lang.String username, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getPersonalEmployeeDetailsByUserStringID(java.lang.String date, java.lang.String userStringId, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getAllEmployeesByJoiningDate(java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String getEmployeesByJoiningMonthandDay(java.lang.String month, java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeDetailByUserNameWithImage(java.lang.String date,java.lang.String userName)throws java.rmi.RemoteException;
    public java.lang.String getEmployeeListWithImage(java.lang.String date, java.lang.String userAceNumber, int relationid) throws java.rmi.RemoteException;
    public java.lang.String getAllPractice() throws java.rmi.RemoteException;
    public java.lang.String getEmployeeListWithRelations() throws java.rmi.RemoteException;
    public void InsertCoDepartmentHead(java.lang.String xmlString) throws java.rmi.RemoteException;
    public void UpdateCoDepartmentHead(java.lang.String xmlString) throws java.rmi.RemoteException;
    
}
