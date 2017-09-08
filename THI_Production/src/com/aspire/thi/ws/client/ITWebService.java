package com.aspire.thi.ws.client;

import java.util.List;


public interface ITWebService {
	
	public void MapTenantTypesToTenant(String param1, String[] param2);
	
	@SuppressWarnings("rawtypes")
	public List GetTenantTypesByTenantId(String param);

	@SuppressWarnings("rawtypes")
	public List GetTenantTypes();

	
//	public String To_get_UserRelations_with_other_users_for_the_given_userACENumber(String loginUserACENumber, int relationid);

}
