package com.aspire.thi.common;

import com.aspire.thi.utils.AppContext;


/**
 * Sync the projects and customers from TAS to the THI application
 * 
 * @author vishnu.nehru
 *
 */
public class SyncProsDataExecutor {
	
	private AppContext context;
	
	public void schedule() {
		context.syncCustomers();
		context.syncDepartments();
		context.syncProsProjects();
	}
	
	public AppContext getContext() {
		return context;
	}

	public void setContext(AppContext context) {
		this.context = context;
	}
}