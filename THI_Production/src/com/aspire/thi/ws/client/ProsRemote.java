/**
 * 
 */
package com.aspire.thi.ws.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author muthu.velappan
 * 
 */
public interface ProsRemote extends Remote {

    public Object getAllCustomers() throws java.rmi.RemoteException;
    public Object getCustomerByAccountManagerUserStringId(java.lang.String accountManagerUserStringId) throws java.rmi.RemoteException;
    public Object fetchAllProjects() throws java.rmi.RemoteException;
    public Object getAllBilledHoursDetailsByDate(java.util.Calendar startDate, int noofMonths) throws java.rmi.RemoteException;
    //public Object getAllYTDBilledHoursDetails(java.util.Calendar queryDate) throws java.rmi.RemoteException;

	public String GetAllActiveCustomer() throws RemoteException;

	public String GetAllActiveProjects() throws RemoteException;

	public String GetAllDeactivateCustomer() throws RemoteException;

	public String GetAllProjects() throws RemoteException;

	public String GetProjectResourceAllocationAndProjectDetailsByAceNumber(
			String ACENumber) throws RemoteException;

	public String GetProjectResourceAllocationAndProjectDetailsByUserStringID(
			String userStringID) throws RemoteException;

	public String GetProjectResourceByProjectName(String projectName)
			throws RemoteException;

	public String GetProjectsByDeliveryUnitstringID(String deliveryUnitStringID)
			throws RemoteException;

	public String GetResourceByCustomerName(String customerName)
			throws RemoteException;

	public String GetResourceLentDetailByACENumber(String aceNumber)
			throws RemoteException;

	public String GetResourceLentDetailByUserStringID(String userStringID)
			throws RemoteException;
	
    public Object getMappedTraineeByUserStringID(java.lang.String userStringId) throws RemoteException;
   
}
