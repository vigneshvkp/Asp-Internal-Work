/**
 * 
 */
package com.aspire.thi.ws.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author muthu.velappan
 * 
 */
public interface ITRemote extends Remote {

	public void MapTenantTypesToTenant(String param1, String[] param2) throws RemoteException;
	
	@SuppressWarnings("rawtypes")
	public List GetTenantTypesByTenantId(String param) throws RemoteException;

	@SuppressWarnings("rawtypes")
	public List GetTenantTypes() throws RemoteException;

}
